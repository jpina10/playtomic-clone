package user.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import user.service.util.validator.ValidationMessages;
import user.service.util.validator.email.ValidEmail;
import user.service.util.validator.password.ValidPassword;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserDto {

    @ValidEmail
    private String email;

    @ValidPassword
    private String password;

    @NotBlank(message = ValidationMessages.CANNOT_BE_NULL_OR_EMPTY)
    private String name;

    @NotBlank(message = ValidationMessages.CANNOT_BE_NULL_OR_EMPTY)
    private String phoneNumber;
}
