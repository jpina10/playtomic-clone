package user.service.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import user.service.dto.LoginRequest;
import user.service.exception.model.UserNotFoundException;
import user.service.exception.validation.SecurityInputValidationException;
import user.service.model.User;
import user.service.repository.UserRepository;

import java.time.Instant;
import java.util.stream.Collectors;

import static user.service.security.SecurityMessages.WRONG_USERNAME_OR_PASSWORD;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private static final long EXPIRES_IN = 300L;
    private static final String ISSUER = "users-api";
    private static final String SCOPE = "scope";

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;

    @Override
    public String generateToken(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.username())
                .orElseThrow(() -> new UserNotFoundException(loginRequest.username()));

        if (!user.getPassword().equals(loginRequest.password())) {
            throw new SecurityInputValidationException(WRONG_USERNAME_OR_PASSWORD);
        }

        var now = Instant.now();
        String roles = user.getRoles().stream().map(Enum::name).collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer(ISSUER)
                .subject(String.valueOf(user.getId()))
                .expiresAt(now.plusSeconds(EXPIRES_IN))
                .issuedAt(now)
                .claim(SCOPE, roles)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
