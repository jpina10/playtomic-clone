package auth.service.jwt;

import auth.service.client.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class JwtServiceImpl implements JwtService {

    private static final long EXPIRES_IN = 300L;
    private static final String ISSUER = "authentication-server";
    private static final String SCOPE = "scope";

    private final JwtEncoder jwtEncoder;

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

    @Cacheable(value = "tokens", key = "#email")
    public String getCachedToken(String email) {

        log.info("cache miss, adding {} and token to cache", email);
        return null;
    }
}
