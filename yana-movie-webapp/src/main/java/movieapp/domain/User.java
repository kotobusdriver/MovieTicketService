package movieapp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@SuperBuilder
public class User {
    String username;
    String password;
    String name;
    String surname;
    UserRole userRole;
}
