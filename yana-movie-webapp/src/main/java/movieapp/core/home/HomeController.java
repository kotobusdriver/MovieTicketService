package movieapp.core.home;

import lombok.RequiredArgsConstructor;
import movieapp.config.MovieAppSession;
import movieapp.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MovieAppSession movieAppSession;

    @Value("${spring.application.name}")
    String appName;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        return "home";
    }

    @GetMapping("/manager-home")
    public String managerHomePage(ModelMap model) {
        User user = movieAppSession.getUser();
        if (user == null) {
            return "home";
        }
        model.addAttribute("username", user.getUsername());
        return "manager-home";
    }

    @GetMapping("/director-home")
    public String directorHomePage(ModelMap model) {
        User user = movieAppSession.getUser();
        if (user == null) {
            return "home";
        }
        model.addAttribute("username", user.getUsername());
        return "director-home";
    }

    @GetMapping("/audience-home")
    public String audienceHomePage(ModelMap model) {
        User user = movieAppSession.getUser();
        if (user == null) {
            return "home";
        }
        model.addAttribute("username", user.getUsername());
        return "audience-home";
    }

}