package test.org.database.dao;

import org.database.dao.ProfessorDAO;
import org.domain.Course;
import org.domain.Professor;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProfessorDAOTest {
    private final int idProfessor = 3;
    private final int idPractitioner = 3;

    @Test
    void addProfessor() throws SQLException {
        Professor professor = new Professor();
        professor.setName("Rafael Lira Santos");
        professor.setPhoneNumber("2281569492");
        professor.setEmail("rafaellirasa@hotmail.com");
        professor.setCubicle(25);
        professor.setStaffNumber("0001-A1");
        Course course = new Course();
        course.setId(1);
        professor.setCourse(course);
        ProfessorDAO professorDAO = new ProfessorDAO();
        int expected = 1;
        int actual = professorDAO.addProfessor(professor);
        System.out.println(actual);
        assertEquals(expected, actual);
    }

    @Test
    void getAssignedProfessorByPractitionerID() throws SQLException {
        ProfessorDAO professorDAO = new ProfessorDAO();
        assertNull( professorDAO.getAssignedProfessorByPractitionerID(idPractitioner) );
    }

    @Test
    public void getAllProfessorsFromLastCourse() throws SQLException {
        ProfessorDAO professorDAO = new ProfessorDAO();
        int expected = 1;
        int actual = professorDAO.getAllProfessorsFromLastCourse().size();
        assertEquals(expected, actual);
    }

    @Test
    void removeProfessor() throws SQLException {
        ProfessorDAO professorDAO = new ProfessorDAO();
        boolean actual = professorDAO.removeProfessorByID(1);
        assertTrue(actual);
    }

}

