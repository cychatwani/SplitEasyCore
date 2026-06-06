package com.splitEasy.core.services.auth;

import com.splitEasy.core.common.utils.AvatarUtils;
import com.splitEasy.core.dto.requests.auth.PasswordRegisterRequestDTO;
import com.splitEasy.core.entity.User;
import com.splitEasy.core.services.user.UserService;
import org.springframework.stereotype.Component;

@Component
public class SplitEasyRegistrationHandler implements PasswordUserRegistrationHandler<PasswordRegisterRequestDTO> {

    private final UserService userService;

    public SplitEasyRegistrationHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Class<PasswordRegisterRequestDTO> supports() {
        return PasswordRegisterRequestDTO.class;
    }

    @Override
    public User createUser(PasswordRegisterRequestDTO request, String passwordHash) {
        String firstName = request.getFirstName().trim();
        String lastName = request.getLastName().trim();
        String avatarUrl = AvatarUtils.generateInitialsAvatarUrl(firstName, lastName);

        return userService.registerPasswordUser(
                firstName,
                lastName,
                request.getEmail(),
                passwordHash,
                avatarUrl
        );
    }
}