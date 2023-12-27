package movieapp.core.adddirector;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Transactional
@AllArgsConstructor
public class AddDirectorController {

    private final AddDirectorService addDirectorService;

    @RequestMapping(value = "/add-director", method = RequestMethod.GET)
    public String showAddDirectorPage(ModelMap model) {
        return "add-director";
    }

    @RequestMapping(value = "/add-director", method = RequestMethod.POST)
    public String saveNewDirector(ModelMap model
            , @RequestParam String username
            , @RequestParam String password
            , @RequestParam String name
            , @RequestParam String surname
            , @RequestParam String nationality
            , @RequestParam int platformId) {

        addDirectorService.addDirector(username, password, name, surname, nationality, platformId);

        model.addAttribute("operationStatus", "Director is created!");
        return "manager-home";
    }
}
