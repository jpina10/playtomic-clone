package user.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Schema(name = "User")
public class UserDto {

    @Schema(description = "email of the user", example = "miro.bernard@example.com")
    private String email;

    @Schema(description = "name of the user", example = "Miro")
    private String name;

    @Schema(description = "phone number of the user", example = "079 046 97 17")
    private String phoneNumber;

}
