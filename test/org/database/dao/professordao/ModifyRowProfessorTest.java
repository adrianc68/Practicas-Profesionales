package org.database.dao.professordao;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import org.database.dao.ProfessorDAO;
import org.domain.ActivityState;
import org.domain.Course;
import org.domain.Professor;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModifyRowProfessorTest {
    static int idProfessor;

    @Test
    public void addProfessor() throws SQLException {
        Professor newProfessor = new Professor();
        newProfessor.setCubicle(35);
        newProfessor.setStaffNumber("RS8434-Z");
        newProfessor.setActivityState(ActivityState.ACTIVE);
        Course course = new Course();
        course.setName("Practicas Profesionales");
        course.setNRC("N104543");
        course.setPeriod("AGO 2020 - DIC 2020");
        course.setId(1);
        newProfessor.setCourse(course);
        newProfessor.setEmail("francv@uv.com.mx");
        newProfessor.setName("Francisco Veraz Veraz");
        newProfessor.setPhoneNumber("2232459685");
        idProfessor = new ProfessorDAO().addProfessor(newProfessor);
        newProfessor.setId(idProfessor);
        int unexpected = 0;
        int actual = idProfessor;
        assertNotEquals(unexpected,actual);
    }

    @Test
    public void addInvalidProfessor() {
        Professor newProfessor = new Professor();
        newProfessor.setCubicle(35);
        newProfessor.setStaffNumber("RS8434-Z");
        newProfessor.setActivityState(ActivityState.ACTIVE);
        Course course = new Course();
        course.setName("Practicas Profesionales");
        course.setNRC("N1045432");
        course.setPeriod("AGO 2020 - DIC 2020");
        course.setId(1);
        newProfessor.setCourse(course);
        newProfessor.setEmail("francvConElCorreoElectronicoMasLargoDelMundoEnEstePlanetaDelaGalaxiaYUniversoActualEsMasDeLaVidaMisma@uv.com.mx");
        newProfessor.setName("Francisco Veraz Veraz");
        newProfessor.setPhoneNumber("2232459685");
        Class<MysqlDataTruncation> expectedException = MysqlDataTruncation.class;
        Class<SQLException> actualException = (Class<SQLException>) assertThrows( SQLException.class, () -> new ProfessorDAO().addProfessor(newProfessor) ).getClass();
        assertEquals(expectedException, actualException);
    }

    @Test
    public void removeProfessorByID() throws SQLException {
        int idProfessorToRemove = idProfessor;
        boolean isProfessorRemoved = new ProfessorDAO().removeProfessorByID(idProfessorToRemove);
        assertTrue(isProfessorRemoved);
    }

    @Test
    public void assignProfessorToPractitioner() throws SQLException {
        int idPractitioner = 8;
        int idProfessor = 5;
        boolean isProfessorAssigned = new ProfessorDAO().assignProfessorToPractitioner(idPractitioner,idProfessor);
        assertTrue(isProfessorAssigned);
    }

    @Test
    public void assignNoExistingProfessorToPractitioner() {
        int idPractitioner = 8;
        int idProfessor = 45455235;
        Class<SQLIntegrityConstraintViolationException> expectedException = SQLIntegrityConstraintViolationException.class;
        Class<SQLIntegrityConstraintViolationException> actualException = (Class<SQLIntegrityConstraintViolationException>) assertThrows( SQLException.class, () ->  new ProfessorDAO().assignProfessorToPractitioner(idPractitioner,idProfessor) ).getClass();
        assertEquals(expectedException, actualException);
    }

    @Test
    public void assignProfessorToNoExistingPractitioner() throws SQLException {
        int idPractitioner = 394203;
        int idProfessor = 5;
        boolean isProfessorAssigned = new ProfessorDAO().assignProfessorToPractitioner(idPractitioner,idProfessor);
        assertFalse(isProfessorAssigned);
    }

}
