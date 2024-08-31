package auth.service.jwt;

import auth.service.dto.LoginRequest;

public interface JwtService {
    String generateToken(LoginRequest loginRequest);
}
