package movieapp.core.viewmovieaveragerating;

import lombok.AllArgsConstructor;
import movieapp.domain.Movie;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@Transactional
@AllArgsConstructor
public class ViewMovieAverageRatingController {

    private final ViewMovieAverageRatingService viewMovieAverageRatingService;

    @RequestMapping(value = "/movie-avg-rating", method = RequestMethod.GET)
    public String showAverageRatingByMovieIdPage(ModelMap model) {
        return "movie-avg-rating";
    }

    @RequestMapping(value = "/movie-avg-rating", method = RequestMethod.POST)
    public String averageRatingByMovieId(ModelMap model, @RequestParam int movieId) {

        Movie movie = viewMovieAverageRatingService.findRatingsByMovieId(movieId);
        model.addAttribute("movie", movie);

        return "movie-avg-rating";
    }
}
