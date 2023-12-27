package movieapp.dataaccess;

import lombok.AllArgsConstructor;
import movieapp.domain.MovieDescriptionView;
import movieapp.domain.MovieSession;
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
public class MovieSessionRepository {

    private final MovieRepository movieRepository;
    private final TheatreRepository theatreRepository;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MovieSession findById(int sessionId) {
        String sql = """
                SELECT *
                FROM MovieSession
                WHERE session_id = :session_id;
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("session_id", sessionId);

        try {
            return jdbcTemplate.queryForObject(sql, parameters, new MovieSessionRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public MovieSession findSessionByAudienceUsernameAndMovieId(String audienceUsername, int movieId) {
        String sql = """
                SELECT s.*
                FROM MovieSession s, Ticket t
                WHERE s.session_id = t.session_id
                    AND t.movie_id = :movie_id
                    AND t.audience_username = :audience_username;
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("movie_id", movieId)
                .addValue("audience_username", audienceUsername);

        try {
            return jdbcTemplate.queryForObject(sql, parameters, new MovieSessionRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void save(MovieSession newMovieSession) {
        String sql = """
                INSERT INTO MovieSession (movie_id, theatre_id, screening_date, from_timeslot, to_timeslot)
                VALUES (:movie_id, :theatre_id, :screening_date, :from_timeslot, :to_timeslot);
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("movie_id", newMovieSession.getMovie().getMovieId())
                .addValue("theatre_id", newMovieSession.getTheatre().getTheatreId())
                .addValue("screening_date", newMovieSession.getScreeningDate())
                .addValue("from_timeslot", newMovieSession.getFromTimeslot())
                .addValue("to_timeslot", newMovieSession.getToTimeslot());

        jdbcTemplate.update(sql, parameters);
    }

    private class MovieSessionRowMapper implements RowMapper<MovieSession> {

        @Override
        public MovieSession mapRow(ResultSet rs, int rowNum) throws SQLException {
            return MovieSession.builder()
                               .sessionId(rs.getInt("session_id"))
                               .movie(movieRepository.findById(rs.getInt("movie_id")))
                               .theatre(theatreRepository.findById(rs.getInt("theatre_id")))
                               .screeningDate(rs.getDate("screening_date"))
                               .fromTimeslot(rs.getInt("from_timeslot"))
                               .build();
        }
    }
}
