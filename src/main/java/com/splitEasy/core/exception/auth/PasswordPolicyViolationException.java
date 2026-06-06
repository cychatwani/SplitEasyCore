package com.splitEasy.core.exception.auth;

import org.springframework.http.HttpStatus;

public class PasswordPolicyViolationException extends AuthException {

    public PasswordPolicyViolationException(String violations) {
        super(violations, "ERR_PASSWORD_POLICY_VIOLATION", HttpStatus.BAD_REQUEST);
    }
}