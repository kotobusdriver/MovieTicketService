package movieapp.core.ratemovie;

import lombok.AllArgsConstructor;
import movieapp.dataaccess.*;
import movieapp.domain.*;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RateMovieService {

    private final AudienceRepository audienceRepository;
    private final MovieSessionRepository movieSessionRepository;
    private final RatingRepository ratingRepository;

    public void rate(int movieId, double userRating, String audienceUsername) {
        Audience audience = audienceRepository.findByUsername(audienceUsername);
        MovieSession movieSession = movieSessionRepository.findSessionByAudienceUsernameAndMovieId(audienceUsername, movieId);
        Rating rating = Rating.builder()
                .audience(audience)
                .movie(movieSession.getMovie())
                .movieSession(movieSession)
                .platform(movieSession.getMovie().getPlatform())
                .rating(userRating)
                .build();

        ratingRepository.save(rating);
    }
}
