package movieapp.config;

import lombok.Data;
import movieapp.domain.User;

@Data
public class MovieAppSession {
    User user;
}
