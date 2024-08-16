package user.service.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import user.service.dto.LoginRequest;
import user.service.dto.LoginResponse;
import user.service.security.jwt.JwtService;

@RestController
@Tag(name = "Auth", description = "Auth")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        String jwtValue = jwtService.generateToken(loginRequest);

        return ResponseEntity.ok(new LoginResponse(jwtValue));
    }
}
