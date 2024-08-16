package user.service.util.guards;


import user.service.util.validator.password.PasswordText;

public class PasswordGuard extends BaseGuard<PasswordText> {
    public PasswordGuard(PasswordText value) {
        super(value);
    }

    public void againstLength(String message) {
        against(value::isLengthInvalid, message);
    }
}
