package movieapp.core.buyticket;

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
public class BuyTicketController {

    private final BuyTicketService buyTicketService;
    private final MovieAppSession movieAppSession;

    @RequestMapping(value = "/buy-ticket", method = RequestMethod.GET)
    public String showBuyTicketPage(ModelMap model) {
        return "buy-ticket";
    }

    @RequestMapping(value = "/buy-ticket", method = RequestMethod.POST)
    public String saveTicket(ModelMap model, @RequestParam int sessionId) {

        String audienceUsername = movieAppSession.getUser().getUsername();

        buyTicketService.buyTicket(sessionId, audienceUsername);

        model.addAttribute("operationStatus", "Ticket is bought!");

        return "audience-home";
    }
}
