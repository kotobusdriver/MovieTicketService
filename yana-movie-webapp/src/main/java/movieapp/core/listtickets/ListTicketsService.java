package movieapp.core.listtickets;

import lombok.AllArgsConstructor;
import movieapp.dataaccess.TicketViewRepository;
import movieapp.domain.TicketView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ListTicketsService {

    private final TicketViewRepository ticketViewRepository;

    public List<TicketView> findTicketsByAudienceUsername(String audienceUsername) {
        return ticketViewRepository.findByAudienceUsername(audienceUsername);
    }
}
