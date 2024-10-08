package auth.service.jwt;

import auth.service.client.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class JwtServiceImpl implements JwtService {

    private static final long EXPIRES_IN = 5L;
    private static final String ISSUER = "authentication-server";
    private static final String SCOPE = "scope";

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    @Override
    @CachePut(value = "tokens", key = "#userDto.email")
    public String generateJwtToken(UserDto userDto) {
        var now = Instant.now();
        String roles = userDto.getRoles().stream().map(Enum::name).collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer(ISSUER)
                .subject(String.valueOf(userDto.getEmail()))
                .expiresAt(now.plusSeconds(EXPIRES_IN))
                .issuedAt(now)
                .claim(SCOPE, roles)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @Override
    public boolean isTokenExpired(String token) {
        Jwt decodedToken = jwtDecoder.decode(token);

        Instant expiresAt = decodedToken.getExpiresAt();

        assert expiresAt != null;
        return Instant.now().isAfter(expiresAt);
    }
}
