package org.database;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DatabasePropertiesTest {
    private final DatabaseProperties databaseProperties;

    public DatabasePropertiesTest() {
        databaseProperties = new DatabaseProperties("databaseTest.properties");
    }

    @Test
    void writeProperties() {
        boolean isPropertieWritten = databaseProperties.writeProperties("anonimo", "12345", "jdbc:mysql://localhost:3306/Pruebas?useTimezone=true&serverTimezone=UTC");
        assertTrue(isPropertieWritten);
    }

    @Test
    void readProperties() {
        Map<String, String> expected = new HashMap<String, String>();
        expected.put("db.user","anonimo");
        expected.put("db.password","12345");
        expected.put("db.url","jdbc:mysql://localhost:3306/Pruebas?useTimezone=true&serverTimezone=UTC");
        Map<String, String> result = databaseProperties.readProperties();
        assertEquals(expected, result);
    }

}