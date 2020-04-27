package org.database.dao;

import org.database.Database;
import org.domain.Course;
import org.domain.Professor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public int addProfessor(Professor professor) {
        return 0;
    }

    @Override
    public boolean removeProfessor(int idprofessor) {
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

    /***
     * Get all professors from actual/last course.
     * <p>
     * This method it's used by the coordinator when he is adding a new professor or
     * assigning a professor to a practitioner.
     * </p>
     * @return List<Professor> a list with all professors from last course.
     */
    @Override
    public List<Professor> getAllProfessorsFromLastCourse() {
        List<Professor> professors = new ArrayList<>();
        try(Connection conn = database.getConnection() ) {
            String statement = "SELECT PROF.id_person, PROF.cubicle, PROF.staff_number, PERSPROF.name, PERSPROF.phoneNumber, PERSPROF.email, COUR.id_course, COUR.NRC, COUR.period, COUR.name FROM Professor AS PROF INNER JOIN PERSON AS PERSPROF ON PROF.id_person = PERSPROF.id_person INNER JOIN COURSE AS COUR ON PERSPROF.id_course = COUR.id_course AND COUR.id_course = (SELECT max(id_course) FROM Course)";
            PreparedStatement queryProfessors = conn.prepareStatement(statement);
            result = queryProfessors.executeQuery();
            while( result.next() ) {
                Course course = new Course();
                course.setId(result.getInt("COUR.id_course"));
                course.setName(result.getString("COUR.name"));
                course.setNRC(result.getString("COUR.NRC"));
                course.setPeriod(result.getString("COUR.period"));
                Professor professor = new Professor();
                professor.setName(result.getString("PERSPROF.name"));
                professor.setPhoneNumber(result.getString("PERSPROF.phoneNumber"));
                professor.setEmail(result.getString("PERSPROF.email"));
                professor.setId(result.getInt("PROF.id_person"));
                professor.setCubicle(result.getInt("PROF.cubicle"));
                professor.setStaffNumber(result.getString("PROF.staff_number"));
                professor.setCourse(course);
                professors.add(professor);
            }
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(ProfessorDAO.class.getName()).log(Level.WARNING, null, e);
        }
        return professors;
    }

}
