package movieapp.dataaccess;

import lombok.AllArgsConstructor;
import movieapp.domain.Movie;
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
public class MovieRepository {

    private final PlatformRepository platformRepository;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public Movie findById(int movieId) {
        String sql = """
                SELECT *
                FROM Movie
                WHERE movie_id = :movie_id;
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("movie_id", movieId);

        try {
            return jdbcTemplate.queryForObject(sql, parameters, new MovieRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Movie> findByDirectorUsername(String directorUsername) {
        String sql = """
                SELECT *
                FROM Movie
                WHERE director_username = :director_username;
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("director_username", directorUsername);

        try {
            return jdbcTemplate.query(sql, parameters, new MovieRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return Collections.EMPTY_LIST;
        }
    }

    public void save(Movie newMovie) {
        String sql = """
                INSERT INTO Movie (movie_id, movie_name, director_username, platform_id, prequel_movie_id, duration)
                VALUES (:movie_id, :movie_name, :director_username, :platform_id, :prequel_movie_id, :duration);
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("movie_id", newMovie.getMovieId())
                .addValue("movie_name", newMovie.getMovieName())
                .addValue("director_username", newMovie.getDirectorUsername())
                .addValue("platform_id", newMovie.getPlatform().getId())
                .addValue("prequel_movie_id", newMovie.getPrequelMovieId())
                .addValue("duration", newMovie.getDuration());

        jdbcTemplate.update(sql, parameters);
    }

    public void update(Movie movie) {
        String sql = """
                UPDATE Movie
                SET movie_name = :movie_name
                    , director_username = :director_username
                    , platform_id = :platform_id
                    , prequel_movie_id = :prequel_movie_id
                    , duration = :duration
                WHERE movie_id = :movie_id;
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("movie_id", movie.getMovieId())
                .addValue("movie_name", movie.getMovieName())
                .addValue("director_username", movie.getDirectorUsername())
                .addValue("platform_id", movie.getPlatform().getId())
                .addValue("prequel_movie_id", movie.getPrequelMovieId())
                .addValue("duration", movie.getDuration());

        jdbcTemplate.update(sql, parameters);
    }

    public void saveOrUpdate(Movie movie) {
        Movie existingMovie = findById(movie.getMovieId());
        if (existingMovie == null) {
            save(movie);
        } else {
            update(movie);
        }
    }

    private class MovieRowMapper implements RowMapper<Movie> {

        @Override
        public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Movie.builder()
                        .movieId(rs.getInt("movie_id"))
                        .movieName(rs.getString("movie_name"))
                        .directorUsername(rs.getString("director_username"))
                        .platform(platformRepository.findById(rs.getInt("platform_id")))
                        .prequelMovieId(rs.getObject("prequel_movie_id", Integer.class))
                        .duration(rs.getInt("duration"))
                        .averageRating(rs.getObject("average_rating", Float.class))
                        .build();
        }
    }
}
