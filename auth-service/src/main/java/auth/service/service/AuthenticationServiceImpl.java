package auth.service.service;

import auth.service.client.UserClient;
import auth.service.dto.LoginRequestDto;
import auth.service.client.dto.UserDto;
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

    @Override
    public String login(LoginRequestDto loginRequestDto) {

        String cachedToken = jwtService.getCachedToken(loginRequestDto.email());

        if (cachedToken != null){
            log.info("cache hit, returning token for email: {}", loginRequestDto.email());
            //validate if is valid
            return cachedToken;
        }

        UserDto userDto = userClient.login(loginRequestDto);

        return jwtService.generateJwtToken(userDto);
    }

}