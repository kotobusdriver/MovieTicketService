package movieapp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@SuperBuilder
public class Movie {

    int movieId;
    String movieName;
    String directorUsername;
    Platform platform;
    Integer prequelMovieId;
    int duration;
    Float averageRating;

    public void link(Integer prequelMovieId) {
        this.prequelMovieId = prequelMovieId;
    }
}
