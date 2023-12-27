package movieapp.core.listmoviesofdirector;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import movieapp.dataaccess.DirectorMovieViewRepository;
import movieapp.domain.DirectorMovieView;

import java.util.List;

@Service
@AllArgsConstructor
public class ListMoviesByDirectorService {

    private final DirectorMovieViewRepository directorMovieViewRepository;

    public List<DirectorMovieView> findMoviesByDirector(String directorUsername) {
        return directorMovieViewRepository.findDirectorMovies(directorUsername);
    }
}
