package org.database.dao.practitionerdao;

import org.database.dao.PractitionerDAO;
import org.domain.Practitioner;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.SQLException;
import java.util.List;

public class GetPractitionerTest {
    @Test
    public void getAllPractitionersFromLastCourse() throws SQLException {
        List<Practitioner> practitionerList = new PractitionerDAO().getAllPractitionersFromLastCourse();
        int expected = 2;
        int actual = practitionerList.size();
        assertEquals(expected,actual);
    }

    @Test
    public void getAllPractitionersByProfessor() throws SQLException {
        int idProfessor = 5;
        List<Practitioner> practitionerList = new PractitionerDAO().getAllPractitionersByProfessor(idProfessor);
        int expected = 1;
        int actual = practitionerList.size();
        assertEquals(expected, actual);
    }

}
