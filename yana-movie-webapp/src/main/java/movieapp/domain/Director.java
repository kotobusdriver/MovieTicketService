package movieapp.domain;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Director extends User {
    String nationality;
    Platform platform;

    @Override
    public UserRole getUserRole() {
        return UserRole.DIRECTOR;
    }

    public void changePlatform(Platform platform) {
        this.platform = platform;
    }
}
