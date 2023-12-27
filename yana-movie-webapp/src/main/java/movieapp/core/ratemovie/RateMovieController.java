package movieapp.core.ratemovie;

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
public class RateMovieController {

    private final RateMovieService rateMovieService;
    private final MovieAppSession movieAppSession;

    @RequestMapping(value = "/rate-movie", method = RequestMethod.GET)
    public String showRateMoviePage(ModelMap model) {
        return "rate-movie";
    }

    @RequestMapping(value = "/rate-movie", method = RequestMethod.POST)
    public String rateMovie(ModelMap model
            , @RequestParam double rating
            , @RequestParam int movieId) {

        String audienceUsername = movieAppSession.getUser().getUsername();

        rateMovieService.rate(movieId, rating, audienceUsername);

        model.addAttribute("operationStatus", "Movie is rated!");
        return "audience-home";
    }
}
