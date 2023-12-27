package movieapp.dataaccess;

import lombok.AllArgsConstructor;
import movieapp.domain.DirectorMovieView;
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
public class DirectorMovieViewRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<DirectorMovieView> findDirectorMovies(String directorUsername) {
        String sql = """
                SELECT m.movie_id
                    , m.movie_name
                    , s.theatre_id
                    , s.from_timeslot
                FROM MovieSession s, Movie m
                WHERE s.movie_id = m.movie_id
                    AND m.director_username = :director_username;
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("director_username", directorUsername);

        try {
            return jdbcTemplate.query(sql, parameters, new DirectorMovieRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private class DirectorMovieRowMapper implements RowMapper<DirectorMovieView> {

        @Override
        public DirectorMovieView mapRow(ResultSet rs, int rowNum) throws SQLException {
            return DirectorMovieView.builder()
                                       .movieId(rs.getInt("movie_id"))
                                       .movieName(rs.getString("movie_name"))
                                       .theatreId(rs.getInt("theatre_id"))
                                       .timeslot(rs.getInt("from_timeslot"))
                                       .build();
        }
    }
}
