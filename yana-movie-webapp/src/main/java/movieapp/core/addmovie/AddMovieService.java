package movieapp.core.addmovie;

import lombok.AllArgsConstructor;
import movieapp.dataaccess.DirectorRepository;
import movieapp.dataaccess.MovieRepository;
import movieapp.dataaccess.MovieSessionRepository;
import movieapp.dataaccess.TheatreRepository;
import movieapp.domain.Director;
import movieapp.domain.Movie;
import movieapp.domain.MovieSession;
import movieapp.domain.Theatre;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class AddMovieService {

    private final DirectorRepository directorRepository;
    private final TheatreRepository theatreRepository;
    private final MovieRepository movieRepository;
    private final MovieSessionRepository movieSessionRepository;

    public void addMovie(int movieId
            , String movieName
            , String directorUsername
            , int duration
            , int theatreId
            , int timeSlot
            , Date date) {
        addMovie(movieId, movieName, directorUsername, null, duration, theatreId, timeSlot, date);
    }

    public void addMovie(int movieId
            , String movieName
            , String directorUsername
            , Integer prequelMovieId
            , int duration
            , int theatreId
            , int timeSlot
            , Date date
    ) {
        Director director = directorRepository.findByUsername(directorUsername);

        Movie movie = Movie.builder()
                              .movieId(movieId)
                              .movieName(movieName)
                              .directorUsername(directorUsername)
                              .platform(director.getPlatform())
                              .prequelMovieId(prequelMovieId)
                              .duration(duration)
                              .build();

        movieRepository.saveOrUpdate(movie);

        Theatre theatre = theatreRepository.findById(theatreId);

        MovieSession newMovieSession = MovieSession.builder()
                                                   .movie(movie)
                                                   .theatre(theatre)
                                                   .screeningDate(date)
                                                   .fromTimeslot(timeSlot)
                                                   .build();
        movieSessionRepository.save(newMovieSession);
    }
}
