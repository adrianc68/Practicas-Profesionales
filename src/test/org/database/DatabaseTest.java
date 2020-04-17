package test.org.database; 

import org.database.Database;
import org.junit.Test;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
* Database Tester.
*
* @author <Angel Adrian Camal Garcia>
* @since <pre>abr 16, 2020</pre>
* @version 1.0
*/
public class DatabaseTest {
    private final Database database;

    public DatabaseTest() {
        database = new Database();
    }

    @Test
    public void connectionToDatabaseSuccesfully() throws SQLException {
        Connection connection = database.getConnection();
        assertNotNull(connection);
    }

    @Test
    public void disconnectFromDatabase() throws SQLException {
        Connection connection = database.getConnection();
        database.disconnect();
        assertTrue(connection.isClosed());
    }

}
