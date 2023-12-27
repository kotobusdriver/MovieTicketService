package movieapp.core.addaudience;

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
public class AddAudienceController {

    private final AddAudienceService addAudienceService;

    @RequestMapping(value = "/add-audience", method = RequestMethod.GET)
    public String showAddAudiencePage(ModelMap model) {
        return "add-audience";
    }

    @RequestMapping(value = "/add-audience", method = RequestMethod.POST)
    public String saveNewAudience(ModelMap model
            , @RequestParam String username
            , @RequestParam String password
            , @RequestParam String name
            , @RequestParam String surname) {

        addAudienceService.addAudience(username, password, name, surname);

        model.addAttribute("operationStatus", "Audience is created!");
        return "manager-home";
    }
}
