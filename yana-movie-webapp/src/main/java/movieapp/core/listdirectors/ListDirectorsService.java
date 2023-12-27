package movieapp.core.listdirectors;

import lombok.AllArgsConstructor;
import movieapp.dataaccess.DirectorRepository;
import movieapp.dataaccess.MovieDescriptionViewRepository;
import movieapp.domain.Director;
import movieapp.domain.MovieDescriptionView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ListDirectorsService {

    private final DirectorRepository directorRepository;

    public List<Director> getAllDirectors() {
        return directorRepository.getAll();
    }
}
