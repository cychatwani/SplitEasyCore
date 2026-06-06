package com.splitEasy.core.dto.requests.auth;

public sealed interface AuthenticationRequest
        permits GoogleAuthRequestDTO, RefreshTokenAuthRequestDto, PasswordAuthRequestDTO  {
}
