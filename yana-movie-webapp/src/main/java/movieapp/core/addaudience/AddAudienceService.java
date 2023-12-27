package movieapp.core.addaudience;

import lombok.AllArgsConstructor;
import movieapp.dataaccess.AudienceRepository;
import movieapp.domain.Audience;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddAudienceService {

    private final AudienceRepository audienceRepository;

    public void addAudience(String username, String password, String name, String surname) {
        Audience newAudience = Audience.builder()
                                    .username(username)
                                    .password(password)
                                    .name(name)
                                    .surname(surname)
                                    .build();

        audienceRepository.save(newAudience);
    }
}
