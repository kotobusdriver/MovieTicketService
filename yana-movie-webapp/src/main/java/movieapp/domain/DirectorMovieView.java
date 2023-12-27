package movieapp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@SuperBuilder
public class DirectorMovieView {
    int movieId;
    String movieName;
    int theatreId;
    int timeslot;
}
