package movieapp.domain;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Audience extends User {

    @Override
    public UserRole getUserRole() {
        return UserRole.AUDIENCE;
    }
}
