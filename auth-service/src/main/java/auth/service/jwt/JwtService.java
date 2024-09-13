package auth.service.jwt;

import auth.service.client.dto.UserDto;

public interface JwtService {
    String generateJwtToken(UserDto user);
    String getCachedToken(String email);
}
