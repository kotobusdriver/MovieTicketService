package movieapp.core.subscribetoplatform;

import lombok.AllArgsConstructor;
import movieapp.config.MovieAppSession;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Transactional
@AllArgsConstructor
public class SubscribeToPlatformController {

    private final SubscribeToPlatformService subscribeToPlatformService;
    private final MovieAppSession movieAppSession;

    @RequestMapping(value = "/subscribe-to-platform", method = RequestMethod.GET)
    public String showSubscribeToPlatformPage(ModelMap model) {
        return "subscribe-to-platform";
    }

    @RequestMapping(value = "/subscribe-to-platform", method = RequestMethod.POST)
    public String subscribeToPlatform(ModelMap model, @RequestParam int platformId) {

        String audienceUsername = movieAppSession.getUser().getUsername();

        subscribeToPlatformService.subscribe(audienceUsername, platformId);


        model.addAttribute("operationStatus", "Subscription is created!");
        return "audience-home";
    }
}
