package movieapp.dataaccess;

import lombok.AllArgsConstructor;
import movieapp.domain.Director;
import movieapp.domain.UserRole;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Repository
public class DirectorRepository {
    private final UserRepository userRepository;

    private final PlatformRepository platformRepository;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public Director findByUsername(String directorUsername) {
        String sql = """
                SELECT u.*, d.nationality, d.platform_id
                FROM Users u, Director d
                WHERE u.username = d.username
                    AND u.username = :username;
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("username", directorUsername);

        try {
            return jdbcTemplate.queryForObject(sql, parameters, new DirectorRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Director> getAll() {
        String sql = """
                SELECT u.*, d.nationality, d.platform_id
                FROM Users u, Director d
                WHERE u.username = d.username;
                """;

        try {
            return jdbcTemplate.query(sql, new DirectorRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return Collections.EMPTY_LIST;
        }
    }

    public void save(Director newDirector) {
        userRepository.save(newDirector);
        doSave(newDirector);
    }

    private void doSave(Director newDirector) {
        String sql = """
                INSERT INTO Director (username, nationality, platform_id, account_type)
                VALUES (:username, :nationality, :platform_id, :account_type);
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("username", newDirector.getUsername())
                .addValue("nationality", newDirector.getNationality())
                .addValue("platform_id", newDirector.getPlatform().getId())
                .addValue("account_type", newDirector.getUserRole().name());

        jdbcTemplate.update(sql, parameters);
    }


    public void update(Director director) {
        String sql = """
                UPDATE Director
                SET nationality = :nationality
                    , platform_id = :platform_id
                    , account_type = :account_type
                WHERE username = :username;
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("username", director.getUsername())
                .addValue("nationality", director.getNationality())
                .addValue("platform_id", director.getPlatform().getId())
                .addValue("account_type", director.getUserRole().name());

        jdbcTemplate.update(sql, parameters);
    }

    private class DirectorRowMapper implements RowMapper<Director> {

        @Override
        public Director mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Director.builder()
                    .username(rs.getString("username"))
                    .password(rs.getString("password"))
                    .name(rs.getString("name"))
                    .surname(rs.getString("surname"))
                    .userRole(UserRole.valueOf(rs.getString("account_type")))
                    .nationality(rs.getString("nationality"))
                    .platform(platformRepository.findById(rs.getInt("platform_id")))
                    .build();
        }
    }
}
