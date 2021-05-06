package org.database.dao.professordao;

import org.database.dao.ProfessorDAO;
import org.domain.Professor;
import org.junit.Test;
import java.sql.SQLException;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class GetProfessorTest {
    @Test
    public void getAssignedProfessorByPractitionerID() throws SQLException {
        int idPractitioner = 8;
        Professor assignedProfessor = new ProfessorDAO().getAssignedProfessorByPractitionerID(idPractitioner);
        String expected = "Juan Carlos";
        String actual = assignedProfessor.getName();
        assertEquals(expected,actual);
    }

    @Test
    public void getAllProfessorsFromLastCourse() throws SQLException {
        List<Professor> professorList = new ProfessorDAO().getAllProfessorsFromLastCourse();
        int expected = 3;
        int actual = professorList.size();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllProfessorsByCourseID() throws SQLException {
        int idCourse = 1;
        List<Professor> professorList = new ProfessorDAO().getAllProfessorsByCourseID(idCourse);
        int expected = 3;
        int actual = professorList.size();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllProfessors() throws SQLException {
        List<Professor> professorList = new ProfessorDAO().getAllProfessors();
        int expected = 3;
        int actual = professorList.size();
        assertEquals(expected,actual);
    }

}