package movieapp.core.login;

import lombok.AllArgsConstructor;
import movieapp.config.MovieAppSession;
import movieapp.domain.UserRole;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("name")
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final MovieAppSession movieAppSession;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage(ModelMap model) {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String showWelcomePage(ModelMap model, @RequestParam String username, @RequestParam String password) {
        UserValidation userValidation = loginService.validateUser(username, password);
        if (userValidation.hasFailed()) {
            model.put("errorMessage", "Invalid Credentials");
            return "login";
        }

        model.put("username", username);

        movieAppSession.setUser(userValidation.getUser());

        if (userValidation.getUserRole() == UserRole.DATABASE_MANAGER) {
            return "manager-home";
        } else if (userValidation.getUserRole() == UserRole.DIRECTOR) {
            return "director-home";
        } else {
            return "audience-home";
        }
    }
}
