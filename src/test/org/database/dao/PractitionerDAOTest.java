package test.org.database.dao;

import org.database.dao.PractitionerDAO;
import org.domain.Course;
import org.domain.Practitioner;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PractitionerDAOTest {

    @Test
    void addPractitioner() {
        Course course = new Course();
        course.setId(1);
        course.setName("Practicas Profesionales");
        course.setNRC("SRC01");
        course.setPeriod("AGO 2020 DIC 2020");
        Practitioner practitioner = new Practitioner();
        practitioner.setName("PRACTICANTE 3");
        practitioner.setEnrollment("S2001223");
        practitioner.setPhoneNumber("2223422543");
        practitioner.setEmail("PRACTICANTE3@HOTMAIL.COM");
        practitioner.setCourse(course);
        PractitionerDAO practitionerDAO = new PractitionerDAO();
        assertTrue(practitionerDAO.addPractitioner(practitioner));
    }

    @Test
    void removePractitioner() {
        PractitionerDAO practitionerDAO = new PractitionerDAO();
        assertTrue(practitionerDAO.removePractitioner(12));
    }

    @Test
    void assignProject() {
        PractitionerDAO practitionerDAO = new PractitionerDAO();
        assertTrue(practitionerDAO.assignProjectToPractitioner(9, 3));
    }

    @Test
    void assignProfessor() {
        PractitionerDAO practitionerDAO = new PractitionerDAO();
        assertTrue(practitionerDAO.assignProfessorToPractitioner(9, 2));
    }

    @Test
    void removeAssignProfessor() {
        PractitionerDAO practitionerDAO = new PractitionerDAO();
        assertTrue(practitionerDAO.removeAssignedProfessorToPractitioner(9));
    }

    @Test
    void getAllPractitionersFromLastCourse() {
        PractitionerDAO practitionerDAO = new PractitionerDAO();
        int expected = 7;
        int result = practitionerDAO.getAllPractitionersFromLastCourse().size();
        assertEquals(expected, result);
    }
}