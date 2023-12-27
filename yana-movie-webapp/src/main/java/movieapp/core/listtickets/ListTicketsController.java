package movieapp.core.listtickets;

import lombok.AllArgsConstructor;
import movieapp.config.MovieAppSession;
import movieapp.domain.TicketView;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@Controller
@Transactional
@AllArgsConstructor
public class ListTicketsController {

    private final ListTicketsService listTicketsService;
    private final MovieAppSession movieAppSession;

    @RequestMapping(value = "/my-tickets", method = RequestMethod.GET)
    public String myTickets(ModelMap model) {

        String audienceUsername = movieAppSession.getUser().getUsername();

        List<TicketView> tickets = listTicketsService.findTicketsByAudienceUsername(audienceUsername);
        model.addAttribute("tickets", tickets);

        return "my-tickets";
    }
}
