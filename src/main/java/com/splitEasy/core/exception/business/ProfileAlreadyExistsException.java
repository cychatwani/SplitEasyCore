package com.splitEasy.core.exception.business;

import org.springframework.http.HttpStatus;

public class ProfileAlreadyExistsException extends BusinessException {

    public ProfileAlreadyExistsException() {
        super("Profile already exists", "ERR_PROFILE_ALREADY_EXISTS", HttpStatus.CONFLICT);
    }
}