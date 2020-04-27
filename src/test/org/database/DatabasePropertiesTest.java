package test.org.database; 

import java.util.HashMap;
import java.util.Map;
import org.database.DatabaseProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/** 
* DatabaseProperties Tester. 
* 
* @author <Angel Adrian Camal Garcia>
* @since <pre>abr 16, 2020</pre> 
* @version 1.0 
*/ 
public class DatabasePropertiesTest {
    private final DatabaseProperties databaseProperties;

    public DatabasePropertiesTest() {
        databaseProperties = new DatabaseProperties();
        databaseProperties.setPath("src/test/org/database/resources/databaseTest.properties");
    }

    @Test
    public void testWriteProperties() {
        assertTrue( databaseProperties.writeProperties("anonimo", "12345", "jdbc:mysql://localhost:3306/PracticesTest?useTimezone=true&serverTimezone=UTC") );
    }

    @Test
    public void testReadProperties() {
        Map<String, String> expected = new HashMap<String, String>();
        expected.put("db.user","anonimo");
        expected.put("db.password","12345");
        expected.put("db.url","jdbc:mysql://localhost:3306/PracticesTest?useTimezone=true&serverTimezone=UTC");
        Map<String, String> result = databaseProperties.readProperties();
        assertEquals(expected, result);
    }


} 
