package movieapp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@AllArgsConstructor
@SuperBuilder
public class MovieSession {
    int sessionId;
    Movie movie;
    Theatre theatre;
    Date screeningDate;
    int fromTimeslot;

    public int getToTimeslot() {
        return fromTimeslot + movie.getDuration();
    }
}
