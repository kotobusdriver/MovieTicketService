package movieapp.dataaccess;

import lombok.AllArgsConstructor;
import movieapp.domain.Ticket;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Repository
public class TicketRepository {

    private final AudienceRepository audienceRepository;
    private final MovieSessionRepository movieSessionRepository;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<Ticket> findByAudienceUsername(String audienceUsername) {
        String sql = """
                SELECT *
                FROM Ticket
                WHERE audience_username = :audience_username;
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("audience_username", audienceUsername);

        try {
            return jdbcTemplate.query(sql, parameters, new TicketRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return Collections.EMPTY_LIST;
        }
    }

    public int countTicketsForSession(int sessionId) {
        String sql = """
                SELECT COUNT(*)
                FROM Ticket
                WHERE session_id = :session_id;
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("session_id", sessionId);

        try {
            return jdbcTemplate.query(sql, parameters, rs -> {
                rs.next();
                return rs.getInt(1);
            });
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    public void save(Ticket ticket) {
        String sql = """
                INSERT INTO Ticket (audience_username, session_id, movie_id, prequel_movie_id)
                VALUES (:audience_username, :session_id, :movie_id, :prequel_movie_id);
                INSERT INTO WatchedMovie (audience_username, movie_id)
                VALUES (:audience_username, :movie_id);
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("audience_username", ticket.getAudience().getUsername())
                .addValue("session_id", ticket.getMovieSession().getSessionId())
                .addValue("movie_id", ticket.getMovieSession().getMovie().getMovieId())
                .addValue("prequel_movie_id", ticket.getMovieSession().getMovie().getPrequelMovieId());

        jdbcTemplate.update(sql, parameters);
    }

    private class TicketRowMapper implements RowMapper<Ticket> {

        @Override
        public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Ticket.builder()
                       .audience(audienceRepository.findByUsername(rs.getString("audience_username")))
                       .movieSession(movieSessionRepository.findById(rs.getInt("session_id")))
                       .build();
        }
    }
}
