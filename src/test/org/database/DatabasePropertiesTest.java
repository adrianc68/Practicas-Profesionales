package test.org.database; 

import java.util.HashMap;
import java.util.Map;
import org.database.DatabaseProperties;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


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
    }

    @Test
    public void testReadProperties() throws Exception {
        Map<String, String> expected = new HashMap<String, String>();
        expected.put("db.user","anonimo");
        expected.put("db.password","12345");
        expected.put("db.url","jdbc:mysql://localhost:3306/practices?useTimezone=true&serverTimezone=UTC");
        Map<String, String> result = databaseProperties.readProperties();
        assertEquals(null, expected, result);
    }


} 
