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
        UserDto userDto = userClient.login(loginRequestDto);

        //add user to cache

        return jwtService.generateJwtToken(userDto);
    }
}