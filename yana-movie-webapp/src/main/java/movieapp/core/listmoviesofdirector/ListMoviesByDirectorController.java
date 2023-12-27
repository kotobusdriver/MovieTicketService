package movieapp.core.listmoviesofdirector;

import lombok.AllArgsConstructor;
import movieapp.domain.DirectorMovieView;
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
public class ListMoviesByDirectorController {

    private final ListMoviesByDirectorService listMoviesByDirectorService;

    @RequestMapping(value = "/movies-by-director", method = RequestMethod.GET)
    public String showListMovieByDirectorPage(ModelMap model) {
        return "movies-by-director";
    }

    @RequestMapping(value = "/movies-by-director", method = RequestMethod.POST)
    public String moviesByDirector(ModelMap model, @RequestParam String directorUsername) {

        List<DirectorMovieView> movies = listMoviesByDirectorService.findMoviesByDirector(directorUsername);
        model.addAttribute("movies", movies);

        return "movies-by-director";
    }
}
