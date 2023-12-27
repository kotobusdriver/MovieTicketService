package movieapp.core.deleteaudience;

import lombok.AllArgsConstructor;
import movieapp.dataaccess.AudienceRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteAudienceService {

    private final AudienceRepository audienceRepository;

    public void delete(String audienceUsername) {
        audienceRepository.deleteByUsername(audienceUsername);
    }
}
