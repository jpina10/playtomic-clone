package user.service.util.validator.user;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ValidationException;
import user.service.util.validator.Text;
import user.service.util.validator.ValidationMessages;
import static user.service.util.Guard.guard;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

    @Override
    public void initialize(ValidUsername constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        try {
            guard(Text.of(username)).againstNullOrWhitespace(ValidationMessages.CANNOT_BE_NULL_OR_EMPTY);
        } catch (ValidationException exception) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(exception.getMessage()).addConstraintViolation();

            return false;
        }

        return true;
    }
}
