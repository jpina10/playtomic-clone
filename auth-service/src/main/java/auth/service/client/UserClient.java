package auth.service.client;

import auth.service.client.dto.UserDto;
import auth.service.dto.LoginRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserClient {

    @PostMapping("/api/v1/users/login")
    UserDto login(@RequestBody LoginRequestDto loginRequestDto);

    @GetMapping("/api/v1/users/{email}")
    UserDto getUserByEmail(@PathVariable String email);
}
