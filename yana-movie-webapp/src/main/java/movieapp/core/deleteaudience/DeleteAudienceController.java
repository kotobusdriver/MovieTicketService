package movieapp.core.deleteaudience;

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
public class DeleteAudienceController {

    private final DeleteAudienceService deleteAudienceService;

    @RequestMapping(value = "/delete-audience", method = RequestMethod.GET)
    public String showDeleteAudiencePage(ModelMap model) {
        return "delete-audience";
    }

    @RequestMapping(value = "/delete-audience", method = RequestMethod.POST)
    public String deleteAudience(ModelMap model, @RequestParam String audienceUsername) {

        deleteAudienceService.delete(audienceUsername);

        model.addAttribute("operationStatus", "Deleted audience!");
        return "manager-home";
    }
}
