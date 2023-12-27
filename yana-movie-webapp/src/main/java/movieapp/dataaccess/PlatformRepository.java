package movieapp.dataaccess;

import lombok.AllArgsConstructor;
import movieapp.domain.Platform;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class PlatformRepository {

    public Platform findById(int platformId) {
        return Platform.fromId(platformId);
    }
}
