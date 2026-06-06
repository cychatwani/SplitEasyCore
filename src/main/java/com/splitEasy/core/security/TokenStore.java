package com.splitEasy.core.security;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class TokenStore {

    private final StringRedisTemplate redisTemplate;

    private String prepareTokenKey(String publicId) {
        return "RefreshToken_" + publicId;
    }

    public void storeRefreshToken(String publicId, String refreshToken, long ttlMillis) {
        redisTemplate.opsForValue()
                .set(prepareTokenKey(publicId), refreshToken, ttlMillis, TimeUnit.MILLISECONDS);
    }

    public Optional<String> getRefreshToken(String publicId) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(prepareTokenKey(publicId)));
    }

    public void deleteRefreshToken(String publicId) {
        redisTemplate.delete(prepareTokenKey(publicId));
    }
}

