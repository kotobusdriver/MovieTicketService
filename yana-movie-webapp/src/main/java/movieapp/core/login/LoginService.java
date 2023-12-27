package movieapp.core.login;

import lombok.AllArgsConstructor;
import movieapp.dataaccess.DatabaseManagerRepository;
import movieapp.dataaccess.UserRepository;
import movieapp.domain.User;
import movieapp.domain.UserRole;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {

    private final DatabaseManagerRepository databaseManagerRepository;
    private final UserRepository userRepository;

    public UserValidation validateUser(String username, String providedPassword) {
        boolean isValidDatabaseManager = databaseManagerRepository.isValidDatabaseManager(username, providedPassword);
        if (isValidDatabaseManager) {
            return new UserValidation(null, UserRole.DATABASE_MANAGER);
        } else {
            User user = userRepository.findValidUser(username, providedPassword);
            if (user != null) {
                return new UserValidation(user, user.getUserRole());
            }
        }
        return UserValidation.FAILED;
    }
}