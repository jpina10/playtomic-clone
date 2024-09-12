package auth.service.service;

import auth.service.dto.LoginRequestDto;

public interface AuthenticationService {

    String login(LoginRequestDto loginRequestDto);
}
