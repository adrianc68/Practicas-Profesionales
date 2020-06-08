package test.org.database.dao;

import org.database.DatabaseProperties;
import org.database.dao.PractitionerDAO;
import org.domain.Course;
import org.domain.Practitioner;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PractitionerDAOTest {
    private DatabaseProperties databaseProperties;
    private final int idPractitioner = 6;
    private final int idProfessor = 2;
    private final int idProject = 1;

    public PractitionerDAOTest() {
        databaseProperties = new DatabaseProperties("database.properties");
    }

    @After
    public void after() {
        databaseProperties.writeProperties("anonimo", "12345", "jdbc:mysql://localhost:3306/Practices?useTimezone=true&serverTimezone=UTC");
    }

    @Before
    public void before() {
        databaseProperties.writeProperties("anonimo", "12345", "jdbc:mysql://localhost:3306/PracticesTest?useTimezone=true&serverTimezone=UTC");
    }

    @Test
    public void addPractitioner() throws SQLException {
        Course course = new Course();
        course.setId(1);
        Practitioner practitioner = new Practitioner();
        practitioner.setName("Roberto Gomez Duran");
        practitioner.setEnrollment("S18014562");
        practitioner.setPhoneNumber("2223422543");
        practitioner.setEmail("zS18012172@estudiantes.uv.mx");
        practitioner.setCourse(course);
        PractitionerDAO practitionerDAO = new PractitionerDAO();
        int actual = practitionerDAO.addPractitioner(practitioner);
        int unexpected = 0;
        assertNotEquals(unexpected, actual);
    }

    @Test
    public void assignProject() {
//        PractitionerDAO practitionerDAO = new PractitionerDAO();
//        assertFalse(practitionerDAO.assignProjectToPractitioner(idPractitioner, idProject));
    }

    @Test
    public void assignProfessor() {
//        PractitionerDAO practitionerDAO = new PractitionerDAO();
//        assertTrue(practitionerDAO.assignProfessorToPractitioner(idPractitioner, idProfessor));
    }

    @Test
    public void getAllPractitionersFromLastCourse() throws SQLException {
        PractitionerDAO practitionerDAO = new PractitionerDAO();
        int expected = 1;
        int result = practitionerDAO.getAllPractitionersFromLastCourse().size();
        assertEquals(expected, result);
    }

    @Test
    public void removeAssignProfessor() {
//        PractitionerDAO practitionerDAO = new PractitionerDAO();
//        assertTrue(practitionerDAO.removeAssignedProfessorToPractitioner(idPractitioner));
    }

    @Test
    public void removePractitioner() {
//        PractitionerDAO practitionerDAO = new PractitionerDAO();
//        assertTrue(practitionerDAO.removePractitioner(idPractitioner));
    }

}