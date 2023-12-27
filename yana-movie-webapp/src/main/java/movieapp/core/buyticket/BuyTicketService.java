package movieapp.core.buyticket;

import lombok.AllArgsConstructor;
import movieapp.dataaccess.AudienceRepository;
import movieapp.dataaccess.MovieSessionRepository;
import movieapp.dataaccess.TicketRepository;
import movieapp.domain.Audience;
import movieapp.domain.MovieSession;
import movieapp.domain.TheatreIsFullException;
import movieapp.domain.Ticket;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BuyTicketService {

    private final AudienceRepository audienceRepository;
    private final MovieSessionRepository movieSessionRepository;
    private final TicketRepository ticketRepository;

    public void buyTicket(int sessionId, String audienceUsername) {

        Audience audience = audienceRepository.findByUsername(audienceUsername);
        MovieSession movieSession = movieSessionRepository.findById(sessionId);
        int ticketsSold = ticketRepository.countTicketsForSession(sessionId);

        if (movieSession.getTheatre().getCapacity() > ticketsSold) {
            Ticket ticket = Ticket.builder()
                                  .audience(audience)
                                  .movieSession(movieSession)
                                  .build();
            ticketRepository.save(ticket);
        } else {
            throw new TheatreIsFullException();
        }

    }
}
