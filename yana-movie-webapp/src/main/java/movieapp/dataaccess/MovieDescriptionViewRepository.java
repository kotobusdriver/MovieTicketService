package movieapp.dataaccess;

import lombok.AllArgsConstructor;
import movieapp.domain.MovieDescriptionView;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
@Repository
public class MovieDescriptionViewRepository {

    private final PlatformRepository platformRepository;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<MovieDescriptionView> findAllMovieDescriptions() {
        String sql = """
                SELECT s.session_id
                    , m.movie_id
                    , m.movie_name
                    , u.surname
                    , m.platform_id
                    , s.theatre_id
                    , s.from_timeslot
                    , m.prequel_movie_id
                FROM MovieSession s, Movie m, Users u
                WHERE s.movie_id = m.movie_id
                    AND m.director_username = u.username;
                """;

        try {
            return jdbcTemplate.query(sql, new MovieDescriptionRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private class MovieDescriptionRowMapper implements RowMapper<MovieDescriptionView> {

        @Override
        public MovieDescriptionView mapRow(ResultSet rs, int rowNum) throws SQLException {
            return MovieDescriptionView.builder()
                                       .sessionId(rs.getInt("session_id"))
                                       .movieId(rs.getInt("movie_id"))
                                       .movieName(rs.getString("movie_name"))
                                       .directorSurname(rs.getString("surname"))
                                       .platformName(platformRepository.findById(rs.getInt("platform_id")).name())
                                       .theatreId(rs.getInt("theatre_id"))
                                       .timeslot(rs.getInt("from_timeslot"))
                                       .predecessorsList(makeStringList(rs.getInt("prequel_movie_id")))
                                       .build();
        }

        private String makeStringList(Integer... prequelMovieIds) {
            String result = "";
            for (int i = 0; i < prequelMovieIds.length; i++) {
                result += prequelMovieIds[i];
                if (i < prequelMovieIds.length - 1) {
                    result += ",";
                }
            }
            return result;
        }
    }
}
