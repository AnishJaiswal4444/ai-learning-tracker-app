package com.ai.tracker.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SessionService {

    private static final String PREFIX = "session:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void saveSession(String username, String token, long expirationMillis) {
        String key = PREFIX + username;
        redisTemplate.opsForValue().set(key, token, expirationMillis, TimeUnit.MILLISECONDS);
    }

    public String getSession(String username) {
        return (String) redisTemplate.opsForValue().get(PREFIX + username);
    }

    public void deleteSession(String username) {
        redisTemplate.delete(PREFIX + username);
    }

    public boolean isTokenValid(String username, String token) {
        String storedToken = getSession(username);
        return token.equals(storedToken);
    }
}
