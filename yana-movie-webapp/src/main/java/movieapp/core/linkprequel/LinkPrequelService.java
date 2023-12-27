package movieapp.core.linkprequel;

import lombok.AllArgsConstructor;
import movieapp.dataaccess.AudienceRepository;
import movieapp.dataaccess.MovieRepository;
import movieapp.dataaccess.MovieSessionRepository;
import movieapp.dataaccess.RatingRepository;
import movieapp.domain.*;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LinkPrequelService {

    private final MovieRepository movieRepository;

    public void link(String directorUsername, int movieId, Integer prequelMovieId) {
        Movie movie = movieRepository.findById(movieId);
        if (movie.getDirectorUsername().equals(directorUsername)) {
            movie.link(prequelMovieId);
            movieRepository.update(movie);
        } else {
            throw new UnauthorizedDirectorException();
        }
    }
}
