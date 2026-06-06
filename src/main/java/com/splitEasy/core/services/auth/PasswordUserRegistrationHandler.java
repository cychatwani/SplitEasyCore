package com.splitEasy.core.services.auth;

import com.splitEasy.core.dto.requests.auth.BasePasswordRegisterRequest;
import com.splitEasy.core.entity.User;

public interface PasswordUserRegistrationHandler<T extends BasePasswordRegisterRequest> {

    Class<T> supports();

    User createUser(T request, String passwordHash);
}