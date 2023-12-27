package movieapp.core.subscribetoplatform;

import lombok.AllArgsConstructor;
import movieapp.dataaccess.AudienceRepository;
import movieapp.dataaccess.PlatformRepository;
import movieapp.dataaccess.SubscriptionRepository;
import movieapp.domain.Audience;
import movieapp.domain.Platform;
import movieapp.domain.Subscription;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubscribeToPlatformService {
    private final AudienceRepository audienceRepository;
    private final PlatformRepository platformRepository;
    private final SubscriptionRepository subscriptionRepository;

    public void subscribe(String audienceUsername, int platformId) {
        Audience audience = audienceRepository.findByUsername(audienceUsername);
        Platform platform = platformRepository.findById(platformId);

        Subscription subscription = Subscription.builder()
                .audience(audience)
                .platform(platform)
                .build();

        subscriptionRepository.save(subscription);
    }
}
