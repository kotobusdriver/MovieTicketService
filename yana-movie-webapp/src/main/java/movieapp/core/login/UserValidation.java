package movieapp.core.login;

import lombok.Value;
import movieapp.domain.User;
import movieapp.domain.UserRole;

@Value
public class UserValidation {

    public static final UserValidation FAILED = new UserValidation(null, null);
    User user;
    UserRole userRole;

    public boolean hasFailed() {
        return userRole == null;
    }
}
