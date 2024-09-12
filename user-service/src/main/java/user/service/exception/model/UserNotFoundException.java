package user.service.exception.model;

public class UserNotFoundException extends ResourceNotFoundException{

    private static final String EMAIL_NOT_FOUND = "User with email %s does not exist.";

    public UserNotFoundException(String value) {
        super(String.format(EMAIL_NOT_FOUND, value));
    }
}
