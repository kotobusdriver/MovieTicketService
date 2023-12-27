package movieapp.dataaccess;

import lombok.AllArgsConstructor;
import movieapp.domain.Audience;
import movieapp.domain.Director;
import movieapp.domain.UserRole;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
@Repository
public class AudienceRepository {

    private final UserRepository userRepository;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public Audience findByUsername(String audienceUsername) {
        String sql = """
                SELECT u.*
                FROM Users u, Audience a
                WHERE u.username = a.username
                    AND u.username = :username;
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("username", audienceUsername);

        try {
            return jdbcTemplate.queryForObject(sql, parameters, new AudienceRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void save(Audience newAudience) {
        userRepository.save(newAudience);
        doSave(newAudience);
    }

    private void doSave(Audience newAudience) {
        String sql = """
                INSERT INTO Audience (username, account_type)
                VALUES (:username, :account_type);
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("username", newAudience.getUsername())
                .addValue("account_type", newAudience.getUserRole().name());

        jdbcTemplate.update(sql, parameters);
    }

    public void deleteByUsername(String audienceUsername) {
        String sql = """
                DELETE FROM Rating WHERE audience_username = :username;
                DELETE FROM Ticket WHERE audience_username = :username;
                DELETE FROM WatchedMovie WHERE audience_username = :username;
                DELETE FROM Subscription WHERE audience_username = :username;
                DELETE FROM Audience WHERE username = :username;
                DELETE FROM Users WHERE username = :username;
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("username", audienceUsername);

        jdbcTemplate.update(sql, parameters);
    }

    private class AudienceRowMapper implements RowMapper<Audience> {

        @Override
        public Audience mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Audience.builder()
                           .username(rs.getString("username"))
                           .password(rs.getString("password"))
                           .name(rs.getString("name"))
                           .surname(rs.getString("surname"))
                           .userRole(UserRole.valueOf(rs.getString("account_type")))
                           .build();
        }
    }
}
