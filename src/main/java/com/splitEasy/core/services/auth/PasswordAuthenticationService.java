package com.splitEasy.core.services.auth;

import com.splitEasy.core.common.utils.EmailUtils;
import com.splitEasy.core.dto.requests.auth.PasswordAuthRequestDTO;
import com.splitEasy.core.dto.response.Auth.AuthResponseDTO;
import com.splitEasy.core.entity.User;
import com.splitEasy.core.enums.AuthProvider;
import com.splitEasy.core.exception.auth.EmailNotVerifiedException;
import com.splitEasy.core.exception.auth.InvalidCredentialsException;
import com.splitEasy.core.services.user.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public final class PasswordAuthenticationService implements AuthenticationProvider<PasswordAuthRequestDTO> {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final SessionIssuer sessionIssuer;

    public PasswordAuthenticationService(UserService userService,
                                         PasswordEncoder passwordEncoder,
                                         SessionIssuer sessionIssuer) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.sessionIssuer = sessionIssuer;
    }

    @Override
    public Class<PasswordAuthRequestDTO> supports() {
        return PasswordAuthRequestDTO.class;
    }

    @Override
    public AuthResponseDTO authenticate(PasswordAuthRequestDTO request) {
        String email = EmailUtils.normalize(request.getEmail());

        User user = userService.getByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        if (user.getAuthProvider() != AuthProvider.PASSWORD) {
            throw new InvalidCredentialsException();
        }

        if (user.getPasswordHash() == null) {
            throw new InvalidCredentialsException();
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        if (!user.isEmailVerified()) {
            throw new EmailNotVerifiedException();
        }

        return sessionIssuer.issueSession(user, AuthProvider.PASSWORD, false);
    }
}