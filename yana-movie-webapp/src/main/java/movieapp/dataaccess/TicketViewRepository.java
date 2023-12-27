package movieapp.dataaccess;

import lombok.AllArgsConstructor;
import movieapp.domain.TicketView;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
@Repository
public class TicketViewRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<TicketView> findByAudienceUsername(String audienceUsername) {
        String sql = """
                SELECT m.movie_id
                    , m.movie_name
                    , t.session_id
                    , r.rating
                    , m.average_rating
                FROM Ticket t, Movie m, Rating r
                WHERE t.movie_id = m.movie_id
                    AND t.movie_id = r.movie_id
                    AND r.audience_username = t.audience_username
                    AND t.audience_username = :audience_username;
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("audience_username", audienceUsername);

        try {
            return jdbcTemplate.query(sql, parameters, new TicketViewRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private class TicketViewRowMapper implements RowMapper<TicketView> {

        @Override
        public TicketView mapRow(ResultSet rs, int rowNum) throws SQLException {
            return TicketView.builder()
                    .movieId(rs.getInt("movie_id"))
                    .movieName(rs.getString("movie_name"))
                    .sessionId(rs.getInt("session_id"))
                    .rating(rs.getInt("rating"))
                    .averageRating(rs.getObject("average_rating", Float.class))
                    .build();
        }
    }
}

