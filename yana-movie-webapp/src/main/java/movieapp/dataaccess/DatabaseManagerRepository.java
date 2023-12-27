package movieapp.dataaccess;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class DatabaseManagerRepository {

    private static final Map<String, String> databaseManagersMap = new HashMap();

    static {
        databaseManagersMap.put("manager1", "managerpass1");
        databaseManagersMap.put("manager2", "managerpass2");
        databaseManagersMap.put("manager35", "managerpass35");
    }

    public boolean isValidDatabaseManager(String username, String providedPassword) {
        if (databaseManagersMap.containsKey(username)) {
            String actualPassword = databaseManagersMap.get(username);
            if (providedPassword.equals(actualPassword)) {
                return true;
            }
        }
        return false;
    }
}
