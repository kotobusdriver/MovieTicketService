package movieapp.dataaccess;

import lombok.AllArgsConstructor;
import movieapp.domain.Subscription;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


@AllArgsConstructor
@Repository
public class SubscriptionRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public void save(Subscription subscription) {
        String sql = """
                INSERT INTO Subscription (audience_username, platform_id)
                VALUES (:audience_username, :platform_id);
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("audience_username", subscription.getAudience().getUsername())
                .addValue("platform_id", subscription.getPlatform().getId());

        jdbcTemplate.update(sql, parameters);
    }
}
