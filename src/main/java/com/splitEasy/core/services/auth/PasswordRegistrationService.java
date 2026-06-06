package com.splitEasy.core.services.auth;

import com.splitEasy.core.common.utils.EmailUtils;
import com.splitEasy.core.dto.requests.auth.BasePasswordRegisterRequest;
import com.splitEasy.core.dto.response.Auth.RegisterResponseDTO;
import com.splitEasy.core.entity.User;
import com.splitEasy.core.exception.auth.PasswordPolicyViolationException;
import com.splitEasy.core.security.JwtService;
import com.splitEasy.core.security.PasswordPolicy;
import com.splitEasy.core.services.notification.NotificationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PasswordRegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final PasswordPolicy passwordPolicy;
    private final JwtService jwtService;
    private final NotificationService notificationService;
    private final List<PasswordUserRegistrationHandler<? extends BasePasswordRegisterRequest>> handlers;

    public PasswordRegistrationService(PasswordEncoder passwordEncoder,
                                       PasswordPolicy passwordPolicy,
                                       JwtService jwtService,
                                       NotificationService notificationService,
                                       List<PasswordUserRegistrationHandler<? extends BasePasswordRegisterRequest>> handlers) {
        this.passwordEncoder = passwordEncoder;
        this.passwordPolicy = passwordPolicy;
        this.jwtService = jwtService;
        this.notificationService = notificationService;
        this.handlers = handlers;
    }

    @SuppressWarnings("unchecked")
    public <T extends BasePasswordRegisterRequest> RegisterResponseDTO register(T request) {
        PasswordUserRegistrationHandler<T> handler = (PasswordUserRegistrationHandler<T>) handlers.stream()
                .filter(h -> h.supports().isAssignableFrom(request.getClass()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No registration handler for " + request.getClass().getSimpleName()));

        List<String> violations = passwordPolicy.validate(request.getPassword());
        if (!violations.isEmpty()) {
            throw new PasswordPolicyViolationException(String.join(", ", violations));
        }

        String email = EmailUtils.normalize(request.getEmail());

        String hash = passwordEncoder.encode(request.getPassword());
        User user = handler.createUser(request, hash);

        String verificationToken = jwtService.generateEmailVerificationToken(user.getPublicId());

        notificationService.send(
                "EMAIL_VERIFICATION",
                "EMAIL",
                email,
                Map.of(
                        "firstName", user.getFirstName(),
                        "link", verificationToken
                )
        );

        return RegisterResponseDTO.builder()
                .email(email)
                .message("Verification email sent. Please check your inbox.")
                .build();
    }
}