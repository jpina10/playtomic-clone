package user.service.exception.model;

public class UserWithPasswordNotFoundException extends ResourceNotFoundException {

    private static final String EMAIL_OR_PASSWORD_NOT_FOUND = "User with email or password does not exist.";

    public UserWithPasswordNotFoundException() {
        super(String.format(EMAIL_OR_PASSWORD_NOT_FOUND));
    }
}
