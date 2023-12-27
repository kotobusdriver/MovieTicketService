package movieapp.core.listmovies;

import lombok.AllArgsConstructor;
import movieapp.dataaccess.MovieDescriptionViewRepository;
import movieapp.domain.MovieDescriptionView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ListMoviesService {

    private final MovieDescriptionViewRepository movieDescriptionViewRepository;

    public List<MovieDescriptionView> getAllMovieDescriptions() {
        return movieDescriptionViewRepository.findAllMovieDescriptions();
    }
}
