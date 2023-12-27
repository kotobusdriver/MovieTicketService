package movieapp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@SuperBuilder
public class Rating {
    Audience audience;
    Movie movie;
    MovieSession movieSession;
    Platform platform;
    double rating;
}
