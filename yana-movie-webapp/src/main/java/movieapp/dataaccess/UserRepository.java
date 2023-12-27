package movieapp.dataaccess;

import lombok.AllArgsConstructor;
import movieapp.domain.User;
import movieapp.domain.UserRole;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
@Repository
public class UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public User findValidUser(String username, String providedPassword) {
        String sql = """
                SELECT *
                FROM Users
                WHERE username = :username
                    AND password = :password;
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("username", username)
                .addValue("password", providedPassword);

        try {
            return jdbcTemplate.queryForObject(sql, parameters, new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void save(User newUser) {
        String sql = """
                INSERT INTO Users (username, password, name, surname, account_type)
                VALUES (:username, :password, :name, :surname, :account_type);
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("username", newUser.getUsername())
                .addValue("password", newUser.getPassword())
                .addValue("name", newUser.getName())
                .addValue("surname", newUser.getSurname())
                .addValue("account_type", newUser.getUserRole().name());

        jdbcTemplate.update(sql, parameters);
    }

    private class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return User.builder()
                       .username(rs.getString("username"))
                       .password(rs.getString("password"))
                       .name(rs.getString("name"))
                       .surname(rs.getString("surname"))
                       .userRole(UserRole.valueOf(rs.getString("account_type")))
                       .build();
        }
    }
}
