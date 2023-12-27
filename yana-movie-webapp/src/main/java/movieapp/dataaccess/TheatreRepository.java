package movieapp.dataaccess;

import lombok.AllArgsConstructor;
import movieapp.domain.Theatre;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
@Repository
public class TheatreRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public Theatre findById(int theatreId) {
        String sql = """
                SELECT *
                FROM Theatre
                WHERE theatre_id = :theatre_id;
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("theatre_id", theatreId);

        try {
            return jdbcTemplate.queryForObject(sql, parameters, new TheatreRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private class TheatreRowMapper implements RowMapper<Theatre> {

        @Override
        public Theatre mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Theatre.builder()
                          .theatreId(rs.getInt("theatre_id"))
                          .theatreName(rs.getString("theatre_name"))
                          .capacity(rs.getInt("capacity"))
                          .build();
        }
    }
}
