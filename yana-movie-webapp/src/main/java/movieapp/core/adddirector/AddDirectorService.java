package movieapp.core.adddirector;

import lombok.AllArgsConstructor;
import movieapp.dataaccess.DirectorRepository;
import movieapp.dataaccess.PlatformRepository;
import movieapp.domain.Director;
import movieapp.domain.Platform;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddDirectorService {

    private final PlatformRepository platformRepository;
    private final DirectorRepository directorRepository;

    public void addDirector(String username, String password, String name, String surname, String nationality,
                            int platformId) {
        Platform platform = platformRepository.findById(platformId);

        Director newDirector = Director.builder()
                                       .username(username)
                                       .password(password)
                                       .name(name)
                                       .surname(surname)
                                       .nationality(nationality)
                                       .platform(platform)
                                       .build();
        directorRepository.save(newDirector);
    }
}
