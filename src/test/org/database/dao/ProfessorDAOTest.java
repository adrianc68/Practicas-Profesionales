package test.org.database.dao;

import org.database.dao.ProfessorDAO;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import static org.junit.jupiter.api.Assertions.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProfessorDAOTest {
    private final int idProfessor = 3;
    private final int idPractitioner = 3;

    @Test
    void addProfessor() {

    }

    @Test
    void getAssignedProfessorByPractitionerID() {
        ProfessorDAO professorDAO = new ProfessorDAO();
        assertNull( professorDAO.getAssignedProfessorByPractitionerID(idPractitioner) );
    }

    @Test
    public void getAllProfessorsFromLastCourse() {
        ProfessorDAO professorDAO = new ProfessorDAO();
        int expected = 1;
        int actual = professorDAO.getAllProfessorsFromLastCourse().size();
        assertEquals(expected, actual);
    }

    @Test
    void removeProfessor() {

    }

}

