package user.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import user.service.util.validator.ValidationMessages;
import user.service.util.validator.password.ValidPassword;
import user.service.util.validator.user.ValidUsername;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserDto {

    @ValidUsername
    private String username;

    @ValidPassword
    private String password;

    private String firstName;
    private String lastName;

    @NotBlank(message = ValidationMessages.CANNOT_BE_NULL_OR_EMPTY)
    private String email;

    private String phoneNumber;
}
