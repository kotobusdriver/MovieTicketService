package movieapp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@SuperBuilder
public class MovieDescriptionView {
    int movieId;
    String movieName;
    String directorSurname;
    String platformName;
    int sessionId;
    int theatreId;
    int timeslot;
    String predecessorsList;
}
