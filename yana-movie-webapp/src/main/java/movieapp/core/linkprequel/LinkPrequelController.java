package movieapp.core.linkprequel;

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
public class LinkPrequelController {

    private final LinkPrequelService linkPrequelService;
    private final MovieAppSession movieAppSession;

    @RequestMapping(value = "/link-prequel", method = RequestMethod.GET)
    public String showLinkPrequelPage(ModelMap model) {
        return "link-prequel";
    }

    @RequestMapping(value = "/link-prequel", method = RequestMethod.POST)
    public String linkPrequel(ModelMap model
            , @RequestParam int movieId
            , @RequestParam Integer prequelMovieId) {

        String directorUsername = movieAppSession.getUser().getUsername();
        linkPrequelService.link(directorUsername, movieId, prequelMovieId);

        model.addAttribute("operationStatus", "Movie is linked as prequel!");
        return "director-home";
    }
}
