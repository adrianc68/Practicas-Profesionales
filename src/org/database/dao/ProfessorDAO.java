package org.database.dao;

import org.database.Database;
import org.domain.Course;
import org.domain.Professor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProfessorDAO implements IProfessorDAO {
    private final Database database;
    private ResultSet result;

    public ProfessorDAO() {
        database = new Database();
    }

    @Override
    public boolean addProfessor(Professor professor) {
        return true;
    }

    @Override
    public boolean removeProfessor(Professor professor) {
        return true;
    }

    /***
     * Get assigned professor of a practicing from datatabase.
     * <p>
     * This method it's used as a support to take an action on a practitioner
     * by the coordinator.
     * </p>
     * @param idPractitioner practitioner's id to get his assigned professor.
     * @return Professor professor assigned to a specific practitioner.
     */
    @Override
    public Professor getAssignedProfessorByPractitionerID(int idPractitioner) {
        Professor professor = null;
        try(Connection conn = database.getConnection() ) {
            String statement = "SELECT PROF.id_person, PROF.cubicle, PROF.staff_number, PERSPROF.name, PERSPROF.phoneNumber, PERSPROF.email, COUR.id_course, COUR.NRC, COUR.period, COUR.name FROM Professor AS PROF INNER JOIN Practitioner AS PRAC ON PROF.id_person = PRAC.id_professor AND PRAC.id_person = ? INNER JOIN PERSON AS PERSPROF ON PROF.id_person = PERSPROF.id_person INNER JOIN COURSE AS COUR ON PERSPROF.id_course = COUR.id_course";
            PreparedStatement queryProfessor = conn.prepareStatement(statement);
            queryProfessor.setInt(1, idPractitioner);
            result = queryProfessor.executeQuery();
            while( result.next() ) {
                Course course = new Course();
                course.setId(result.getInt("COUR.id_course"));
                course.setName(result.getString("COUR.name"));
                course.setNRC(result.getString("COUR.NRC"));
                course.setPeriod(result.getString("COUR.period"));
                professor = new Professor();
                professor.setName(result.getString("PERSPROF.name"));
                professor.setPhoneNumber(result.getString("PERSPROF.phoneNumber"));
                professor.setEmail(result.getString("PERSPROF.email"));
                professor.setId(result.getInt("PROF.id_person"));
                professor.setCubicle(result.getInt("PROF.cubicle"));
                professor.setStaffNumber(result.getString("PROF.staff_number"));
                professor.setCourse(course);
            }
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(ProfessorDAO.class.getName()).log(Level.WARNING, null, e);
        }
        return professor;
    }

    @Override
    public List<Professor> getAllProfessors() {
        return null;
    }

}
