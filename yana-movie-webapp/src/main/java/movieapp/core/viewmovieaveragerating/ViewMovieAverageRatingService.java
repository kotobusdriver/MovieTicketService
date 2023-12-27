package movieapp.core.viewmovieaveragerating;

import lombok.AllArgsConstructor;
import movieapp.dataaccess.MovieRepository;
import movieapp.domain.Movie;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ViewMovieAverageRatingService {

    private final MovieRepository movieRepository;

    public Movie findRatingsByMovieId(int movieId) {
        return movieRepository.findById(movieId);
    }
}
