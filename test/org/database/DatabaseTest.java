package org.database;

import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class DatabaseTest {
    private final Database database;

    public DatabaseTest() {
        database = new Database();
        database.setAnotherDatabaseProperties("databaseTest.properties");
    }

    @Test
    void getConnection() throws SQLException {
        Connection connection = database.getConnection();
        assertNotNull(connection);
    }

    @Test
    void disconnect() {
        Connection connection = database.disconnect();
        assertNull(connection);
    }

}