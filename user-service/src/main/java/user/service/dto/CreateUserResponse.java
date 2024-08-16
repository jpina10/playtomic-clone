package user.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserResponse")
public record CreateUserResponse(
        @Schema(description = "username of the user", example = "sadladybug779") String username) {
}
