package auth.service.jwt;

import auth.service.dto.LoginRequest;
import auth.service.exception.model.UserNotFoundException;
import auth.service.exception.validation.SecurityInputValidationException;
import auth.service.model.User;
import auth.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

import static auth.service.message.SecurityMessages.WRONG_USERNAME_OR_PASSWORD;


@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private static final long EXPIRES_IN = 300L;
    private static final String ISSUER = "authentication-server";
    private static final String SCOPE = "scope";

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;

    @Override
    public String generateToken(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new UserNotFoundException(loginRequest.email()));

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
