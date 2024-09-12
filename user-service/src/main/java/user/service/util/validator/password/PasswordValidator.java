package user.service.util.validator.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ValidationException;
import user.service.util.validator.Text;

import static user.service.util.Guard.guard;
import static user.service.util.validator.ValidationMessages.CANNOT_BE_NULL_OR_EMPTY;
import static user.service.util.validator.ValidationMessages.PASSWORD_CANNOT_BE_INFERIOR;


public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        try {
            guard(Text.of(password)).againstNullOrWhitespace(CANNOT_BE_NULL_OR_EMPTY);
            guard(password).againstLength(PASSWORD_CANNOT_BE_INFERIOR);
        } catch (ValidationException exception) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(exception.getMessage()).addConstraintViolation();

            return false;
        }

        return true;
    }
}
