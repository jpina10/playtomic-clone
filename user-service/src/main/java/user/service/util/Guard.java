package user.service.util;

import lombok.experimental.UtilityClass;
import user.service.util.guards.ObjectGuard;
import user.service.util.guards.PasswordGuard;
import user.service.util.guards.TextGuard;
import user.service.util.validator.Text;
import user.service.util.validator.password.PasswordText;

@UtilityClass
public class Guard {

    public static PasswordGuard guard(String value) {
        return new PasswordGuard(new PasswordText(value));
    }

    public static TextGuard guard(Text value) {
        return new TextGuard(value);
    }

    public static ObjectGuard guard(Object value) {
        return new ObjectGuard(value);
    }
}
