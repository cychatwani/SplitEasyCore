package com.splitEasy.core.services.auth;

import com.splitEasy.core.dto.requests.auth.AuthenticationRequest;
import com.splitEasy.core.dto.response.Auth.AuthResponseDTO;

/**
 * Provider SPI: one implementation per auth flow (Google, Refresh, Apple, Password, ...)
 */
public interface AuthenticationProvider<T extends AuthenticationRequest> {
    /** concrete request class handled by this provider */
    Class<T> supports();

    /** authenticate and return AuthResponseDTO */
    AuthResponseDTO authenticate(T request);
}
