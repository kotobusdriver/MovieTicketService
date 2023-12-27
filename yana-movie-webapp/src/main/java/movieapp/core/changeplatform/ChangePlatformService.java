package movieapp.core.changeplatform;

import lombok.AllArgsConstructor;
import movieapp.dataaccess.DirectorRepository;
import movieapp.dataaccess.PlatformRepository;
import movieapp.domain.Director;
import movieapp.domain.Platform;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChangePlatformService {

    private final PlatformRepository platformRepository;
    private final DirectorRepository directorRepository;

    public void changePlatform(String directorUsername, int platformId) {
        Platform platform = platformRepository.findById(platformId);
        Director director = directorRepository.findByUsername(directorUsername);
        director.changePlatform(platform);
        directorRepository.update(director);
    }
}
