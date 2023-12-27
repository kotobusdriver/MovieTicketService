package movieapp.core.listratingsbyaudience;

import lombok.AllArgsConstructor;
import movieapp.domain.Rating;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@Transactional
@AllArgsConstructor
public class ListRatingsByAudienceController {

    private final ListRatingsByAudienceService listRatingsByAudienceService;

    @RequestMapping(value = "/ratings-by-audience", method = RequestMethod.GET)
    public String showListRatingsByAudiencePage(ModelMap model) {
        return "ratings-by-audience";
    }

    @RequestMapping(value = "/ratings-by-audience", method = RequestMethod.POST)
    public String linkPrequel(ModelMap model, @RequestParam String audienceUsername) {

        List<Rating> ratings = listRatingsByAudienceService.findRatingsByAudience(audienceUsername);
        model.addAttribute("ratings", ratings);

        return "ratings-by-audience";
    }
}
