package org.database.dao.persondao;

import org.database.dao.PersonDAO;
import org.domain.ActivityState;
import org.domain.Person;
import org.junit.jupiter.api.Test;
import org.util.Cryptography;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PersonDAOTest {

    @Test
    void getActivityStateByID() throws SQLException {
        int idPerson = 4;
        ActivityState expected = ActivityState.ACTIVE;
        ActivityState actual = new PersonDAO().getActivityStateByID(4);
        assertEquals(expected,actual);
    }

    @Test
    void changeActivityStateByID() throws SQLException {
        int idPerson = 5;
        boolean isActivityStateChanged = new PersonDAO().changeActivityStateByID(idPerson);
        assertTrue(isActivityStateChanged);
    }

    @Test
    void getPersonByEmailAndPassword() throws SQLException {
        String email = "aj@uv.com.mx";
        String password = Cryptography.cryptSHA2( "1");
        Person person = new PersonDAO().getPersonByEmailAndPassword(email,password);
        String nameExpected = "Angel Juan";
        String actual = person.getName();
        assertEquals(nameExpected,actual);
    }
}