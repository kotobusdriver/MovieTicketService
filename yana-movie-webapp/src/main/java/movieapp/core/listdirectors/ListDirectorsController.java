package movieapp.core.listdirectors;

import lombok.AllArgsConstructor;
import movieapp.domain.Director;
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
public class ListDirectorsController {

    private final ListDirectorsService listDirectorsService;

    @RequestMapping(value = "/list-directors", method = RequestMethod.GET)
    public String showDirectorsPage(ModelMap model) {
        List<Director> directors = listDirectorsService.getAllDirectors();

        model.addAttribute("directors", directors);

        return "list-directors";
    }
}
