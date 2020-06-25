package org.database.dao.practitionerdao;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import org.database.dao.PractitionerDAO;
import org.domain.ActivityState;
import org.domain.Course;
import org.domain.Practitioner;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModifyRowPractitionerTest {
    static int idPractitioner;

    @Test
    public void addPractitioner() throws SQLException {
        Practitioner newPractitioner = new Practitioner();
        newPractitioner.setProfessor(null);
        newPractitioner.setProject(null);
        newPractitioner.setEnrollment("S19023349");
        newPractitioner.setSelectedProjects(null);
        newPractitioner.setDeliveries(null);
        newPractitioner.setActivityState(ActivityState.ACTIVE);
        Course course = new Course();
        course.setName("Practicas Profesionales");
        course.setNRC("N104543");
        course.setPeriod("AGO 2020 - DIC 2020");
        course.setId(1);
        newPractitioner.setCourse(course);
        newPractitioner.setEmail("zS19023349@estudiantes.uv.mx");
        newPractitioner.setName("Ricardo Rico Inoue");
        newPractitioner.setPhoneNumber("2293456823");
        idPractitioner = new PractitionerDAO().addPractitioner(newPractitioner);
        newPractitioner.setId(idPractitioner);
        int unexpected = 0;
        int actual = idPractitioner;
        assertNotEquals(unexpected,actual);
    }

    @Test
    public void addInvalidPractitioner() {
        Practitioner newPractitioner = new Practitioner();
        newPractitioner.setProfessor(null);
        newPractitioner.setProject(null);
        newPractitioner.setEnrollment("S18093449");
        newPractitioner.setSelectedProjects(null);
        newPractitioner.setDeliveries(null);
        newPractitioner.setActivityState(ActivityState.ACTIVE);
        Course course = new Course();
        course.setName("Practicas Profesionales");
        course.setNRC("N104543");
        course.setPeriod("AGO 2020 - DIC 2020");
        course.setId(1);
        newPractitioner.setCourse(course);
        newPractitioner.setEmail("zS19023349@estudiantes.uv.mx");
        newPractitioner.setName("Ricardo Rico Inoue Ricardo Rico Inoue Ricardo Rico Inoue Ricardo Rico Inoue Ricardo Rico Inoue Ricardo Rico Inoue Ricardo Rico Inoue");
        newPractitioner.setPhoneNumber("2345621423");
        Class<MysqlDataTruncation> expectedException = MysqlDataTruncation.class;
        Class<SQLException> actualException = (Class<SQLException>) assertThrows( SQLException.class, () -> new PractitionerDAO().addPractitioner(newPractitioner) ).getClass();
        assertEquals(expectedException, actualException);
    }

    @Test
    public void removePractitioner() throws SQLException {
        int idPractitionerToRemove = idPractitioner;
        boolean isPractitionerRemoved = new PractitionerDAO().removePractitioner(idPractitionerToRemove);
        assertTrue(isPractitionerRemoved);
    }

    @Test
    public void removePractitionerWhoHasSelectProject() {
        int idPractitionerToRemove = 9;
        Class<SQLIntegrityConstraintViolationException> expectedException = SQLIntegrityConstraintViolationException.class;
        Class<SQLIntegrityConstraintViolationException> actualException = (Class<SQLIntegrityConstraintViolationException>) assertThrows( SQLException.class, () ->  new PractitionerDAO().removePractitioner(idPractitionerToRemove) ).getClass();
        assertEquals(expectedException, actualException);
    }

}