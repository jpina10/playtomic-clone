package auth.service.service;

import auth.service.client.UserClient;
import auth.service.client.dto.UserDto;
import auth.service.dto.LoginRequestDto;
import auth.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserClient userClient;
    private final JwtService jwtService;
    private final CacheService cacheService;

    @Override
    public String login(LoginRequestDto loginRequestDto) {

        String email = loginRequestDto.email();

        String cachedToken = cacheService.getCachedToken(email);

        if (cachedToken != null) {
            log.info("cache hit, checking expiry date of token for email: {}", email);

            boolean isExpired = jwtService.isTokenExpired(cachedToken);

            if (!isExpired) {
                return cachedToken;
            }

            log.info("token expired, removing cache entry");
            cacheService.removeCachedToken(email);

            log.info("validating credentials and generating new token...");
        }

        UserDto userDto = userClient.login(loginRequestDto);

        return jwtService.generateJwtToken(userDto);
    }

}