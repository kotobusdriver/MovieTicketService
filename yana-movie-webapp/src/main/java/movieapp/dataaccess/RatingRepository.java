package movieapp.dataaccess;

import lombok.AllArgsConstructor;
import movieapp.domain.Audience;
import movieapp.domain.MovieSession;
import movieapp.domain.Rating;
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
public class RatingRepository {

    private final AudienceRepository audienceRepository;
    private final MovieSessionRepository movieSessionRepository;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<Rating> findByAudienceUsername(String audienceUsername) {
        String sql = """
                SELECT *
                FROM Rating
                WHERE audience_username = :audience_username;
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("audience_username", audienceUsername);

        try {
            return jdbcTemplate.query(sql, parameters, new RatingRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return Collections.EMPTY_LIST;
        }
    }

    public void save(Rating rating) {
        String sql = """
                INSERT INTO Rating (audience_username, movie_id, session_id, platform_id, rating)
                VALUES (:audience_username, :movie_id, :session_id, :platform_id, :rating);
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("audience_username", rating.getAudience().getUsername())
                .addValue("movie_id", rating.getMovie().getMovieId())
                .addValue("session_id", rating.getMovieSession().getSessionId())
                .addValue("platform_id", rating.getPlatform().getId())
                .addValue("rating", rating.getRating());

        jdbcTemplate.update(sql, parameters);
    }

    private class RatingRowMapper implements RowMapper<Rating> {

        @Override
        public Rating mapRow(ResultSet rs, int rowNum) throws SQLException {
            Audience audience = audienceRepository.findByUsername(rs.getString("audience_username"));
            MovieSession movieSession = movieSessionRepository.findById(rs.getInt("session_id"));
            return Rating.builder()
                    .audience(audience)
                    .movie(movieSession.getMovie())
                    .movieSession(movieSession)
                    .platform(movieSession.getMovie().getPlatform())
                    .rating(rs.getInt("rating"))
                    .build();
        }
    }
}
