package user.service.security.jwt;

import user.service.dto.LoginRequest;

public interface JwtService {
    String generateToken(LoginRequest loginRequest);
}
