package movieapp.core.listmovies;

import lombok.AllArgsConstructor;
import movieapp.domain.MovieDescriptionView;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@Controller
@Transactional
@AllArgsConstructor
public class ListMoviesController {

    private final ListMoviesService listMoviesService;

    @RequestMapping(value = "/list-movies", method = RequestMethod.GET)
    public String showListMoviesPage(ModelMap model) {
        List<MovieDescriptionView> movieDescriptions = listMoviesService.getAllMovieDescriptions();

        model.addAttribute("movies", movieDescriptions);

        return "list-movies";
    }
}
