package movieapp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@SuperBuilder
public class TicketView {
    int movieId;
    String movieName;
    int sessionId;
    double rating;
    Float averageRating;
}
