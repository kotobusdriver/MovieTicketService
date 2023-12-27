package movieapp.core.listratingsbyaudience;

import lombok.AllArgsConstructor;
import movieapp.dataaccess.RatingRepository;
import movieapp.domain.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ListRatingsByAudienceService {

    private final RatingRepository ratingRepository;

    public List<Rating> findRatingsByAudience(String audienceUsername) {
        return ratingRepository.findByAudienceUsername(audienceUsername);
    }
}
