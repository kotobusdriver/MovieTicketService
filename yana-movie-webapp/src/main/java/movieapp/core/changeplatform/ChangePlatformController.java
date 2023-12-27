package movieapp.core.changeplatform;

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
public class ChangePlatformController {

    private final ChangePlatformService changePlatformService;

    @RequestMapping(value = "/change-platform", method = RequestMethod.GET)
    public String showChangePlatformPage(ModelMap model) {
        return "change-platform";
    }

    @RequestMapping(value = "/change-platform", method = RequestMethod.POST)
    public String changePlatform(ModelMap model
            , @RequestParam String directorUsername
            , @RequestParam int platformId) {

        changePlatformService.changePlatform(directorUsername, platformId);

        model.addAttribute("operationStatus", "Changed director's platform!");
        return "manager-home";
    }
}
