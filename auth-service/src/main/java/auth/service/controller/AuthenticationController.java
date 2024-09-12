package auth.service.controller;

import auth.service.dto.AccessTokenResponse;
import auth.service.dto.LoginRequestDto;
import auth.service.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginRequestDto loginRequestDto) {
        String jwtToken = authenticationService.login(loginRequestDto);

        return ResponseEntity.ok(new AccessTokenResponse(jwtToken));
    }
}
