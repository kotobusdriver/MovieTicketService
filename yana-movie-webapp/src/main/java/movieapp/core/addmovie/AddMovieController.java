package movieapp.core.addmovie;

import lombok.AllArgsConstructor;
import movieapp.config.MovieAppSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;


@Controller
@Transactional
@AllArgsConstructor
public class AddMovieController {

    private final AddMovieService addMovieService;
    private final MovieAppSession movieAppSession;

    @RequestMapping(value = "/add-movie", method = RequestMethod.GET)
    public String showAddMoviePage(ModelMap model) {
        return "add-movie";
    }

    @RequestMapping(value = "/add-movie", method = RequestMethod.POST)
    public String saveNewMovie(ModelMap model
            , @RequestParam int movieId
            , @RequestParam String movieName
            , @RequestParam int theatreId
            , @RequestParam int timeslot
            , @RequestParam int duration
            , @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {

        String directorUsername = movieAppSession.getUser().getUsername();

        addMovieService.addMovie(movieId, movieName, directorUsername, duration, theatreId, timeslot, date);

        model.addAttribute("operationStatus", "Movie is created!");

        return "director-home";
    }
}
