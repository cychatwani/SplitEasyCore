package com.splitEasy.core.services.auth;

import com.splitEasy.core.dto.requests.auth.AuthenticationRequest;
import com.splitEasy.core.dto.response.Auth.AuthResponseDTO;

public interface AuthenticationService {
    AuthResponseDTO authenticate(AuthenticationRequest request);
}
