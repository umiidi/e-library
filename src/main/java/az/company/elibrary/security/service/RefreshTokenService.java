package az.company.elibrary.security.service;

import az.company.elibrary.exception.type.RefreshTokenExpiredException;
import az.company.elibrary.security.properties.SecurityProperties;
import az.company.elibrary.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService implements TokenCreator<String>, TokenReader<String> {

    private static final String REFRESH_TOKEN_KEY_FORMAT = "user:refresh_token:%s";

    private final SecurityProperties securityProperties;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public String create(String email) {
        String refreshToken = UUID.randomUUID().toString();
        store(email, refreshToken);
        return refreshToken;
    }

    @Override
    public String read(String token) {
        // todo: optimize this code

        String keyPattern = String.format(REFRESH_TOKEN_KEY_FORMAT, "*");
        Set<String> keys = redisTemplate.keys(keyPattern);
        for (String key : keys) {
            String storedToken = redisTemplate.opsForValue().get(key);
            if (storedToken != null && storedToken.equals(token)) {
                return key.substring(key.lastIndexOf(":") + 1);
            }
        }
        throw new RefreshTokenExpiredException("refresh token expired");
    }

    public void delete(String email) {
        String key = String.format(REFRESH_TOKEN_KEY_FORMAT, email);
        redisTemplate.delete(key);
    }

    private void store(String email, String refreshToken) {
        String key = String.format(REFRESH_TOKEN_KEY_FORMAT, email);
        long refreshTokenValidity = securityProperties.getJwtProperties().getRefreshTokenValidityInSeconds();
        redisTemplate.opsForValue().set(key, refreshToken, refreshTokenValidity, TimeUnit.SECONDS);
    }

}
