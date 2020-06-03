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
    private ResultSet resultSet;

    /***
     * ProfessorDAO constructor.
     * This constructor initialize a connection to the database.
     */
    public ProfessorDAO() {
        database = new Database();
    }

    /***
     * Add a new professor to Course.
     * <p>
     * This method is used by Administrator when he needs someone to
     * manage the activities of a course.
     * </p>
     * @param professor the professor to be added to database.
     * @return int representing the professor's id.
     */
    @Override
    public int addProfessor(Professor professor) {
        int idProfessor = 0;
        try(Connection conn = database.getConnection() ){
            conn.setAutoCommit(false);
            String statement = "CALL addProfessor(?, ?, ?, ?, ?, ?)";
            PreparedStatement insertProfessor = conn.prepareStatement(statement);
            insertProfessor.setString( 1, professor.getName() );
            insertProfessor.setString(2, professor.getPhoneNumber() );
            insertProfessor.setString(3, professor.getEmail() );
            insertProfessor.setInt(4, professor.getCourse().getId() );
            insertProfessor.setInt(5, professor.getCubicle() );
            insertProfessor.setString(6, professor.getStaffNumber() );
            insertProfessor.executeUpdate();
            statement = "SELECT LAST_INSERT_ID()";
            insertProfessor = conn.prepareStatement(statement);
            resultSet = insertProfessor.executeQuery();
            resultSet.next();
            idProfessor = resultSet.getInt(1);
            conn.commit();
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger( ProfessorDAO.class.getName() ).log(Level.WARNING, null, e);
        }
        return idProfessor;
    }

    /***
     * Remove a professor from course.
     * <p>
     * This method is used by Administrator when he needs to remove a professor
     * for any reason.
     * </p>
     * @param idProfessor professor's id to be removed.
     * @return boolean true if professor was deleted from database.
     */
    @Override
    public boolean removeProfessorByID(int idProfessor) {
        int rowsAffected = 0;
        try(Connection conn = database.getConnection() ){
            conn.setAutoCommit(false);
            String statement = "DELETE FROM Professor WHERE id_person = ?";
            PreparedStatement deleteProfessor = conn.prepareStatement(statement);
            deleteProfessor.setInt( 1, idProfessor );
            rowsAffected = deleteProfessor.executeUpdate();
            conn.commit();
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger( ProfessorDAO.class.getName() ).log(Level.WARNING, null, e);
        }
        return rowsAffected > 0;
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
            String statement = "SELECT PROF.id_person, PROF.cubicle, PROF.staff_number, PERSPROF.name, PERSPROF.phoneNumber, PERSPROF.email, PERSPROF.activity_state, COUR.id_course, COUR.NRC, COUR.period, COUR.name FROM Professor AS PROF INNER JOIN Practitioner AS PRAC ON PROF.id_person = PRAC.id_professor AND PRAC.id_person = ? INNER JOIN PERSON AS PERSPROF ON PROF.id_person = PERSPROF.id_person INNER JOIN COURSE AS COUR ON PERSPROF.id_course = COUR.id_course";
            PreparedStatement queryProfessor = conn.prepareStatement(statement);
            queryProfessor.setInt(1, idPractitioner);
            resultSet = queryProfessor.executeQuery();
            while( resultSet.next() ) {
                Course course = new Course();
                course.setId( resultSet.getInt("COUR.id_course") );
                course.setName( resultSet.getString("COUR.name") );
                course.setNRC( resultSet.getString("COUR.NRC") );
                course.setPeriod( resultSet.getString("COUR.period") );
                professor = new Professor();
                professor.setName( resultSet.getString("PERSPROF.name") );
                professor.setPhoneNumber( resultSet.getString("PERSPROF.phoneNumber") );
                professor.setEmail( resultSet.getString("PERSPROF.email") );
                professor.setActivityState( resultSet.getString("PERSPROF.activity_state") );
                professor.setId( resultSet.getInt("PROF.id_person") );
                professor.setCubicle( resultSet.getInt("PROF.cubicle") );
                professor.setStaffNumber( resultSet.getString("PROF.staff_number") );
                professor.setCourse(course);
            }
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger( ProfessorDAO.class.getName() ).log(Level.WARNING, null, e);
        }
        return professor;
    }

    /***
     * Get all professors from last/actual course
     * <p>
     * This method it's used by Coordinator when he is assigning a
     * professor to a practitioner.
     * </p>
     * @return List<Professor> a list with all professors from last course.
     */
    @Override
    public List<Professor> getAllProfessorsFromLastCourse() {
        List<Professor> professors = new ArrayList<>();
        try(Connection conn = database.getConnection() ) {
            String statement = "SELECT PROF.id_person, PROF.cubicle, PROF.staff_number, PERSPROF.name, PERSPROF.phoneNumber, PERSPROF.email, PERSPROF.activity_state, COUR.id_course, COUR.NRC, COUR.period, COUR.name FROM Professor AS PROF INNER JOIN PERSON AS PERSPROF ON PROF.id_person = PERSPROF.id_person INNER JOIN COURSE AS COUR ON PERSPROF.id_course = COUR.id_course AND COUR.id_course = (SELECT max(id_course) FROM Course)";
            PreparedStatement queryProfessors = conn.prepareStatement(statement);
            resultSet = queryProfessors.executeQuery();
            while( resultSet.next() ) {
                Course course = new Course();
                course.setId( resultSet.getInt("COUR.id_course") );
                course.setName( resultSet.getString("COUR.name") );
                course.setNRC( resultSet.getString("COUR.NRC") );
                course.setPeriod( resultSet.getString("COUR.period") );
                Professor professor = new Professor();
                professor.setName( resultSet.getString("PERSPROF.name") );
                professor.setPhoneNumber( resultSet.getString("PERSPROF.phoneNumber") );
                professor.setActivityState( resultSet.getString("PERSPROF.activity_state") );
                professor.setEmail( resultSet.getString("PERSPROF.email") );
                professor.setId( resultSet.getInt("PROF.id_person") );
                professor.setCubicle( resultSet.getInt("PROF.cubicle") );
                professor.setStaffNumber( resultSet.getString("PROF.staff_number") );
                professor.setCourse(course);
                professors.add(professor);
            }
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger( ProfessorDAO.class.getName() ).log(Level.WARNING, null, e);
        }
        return professors;
    }

    /***
     * Get all professors by a specified course
     * <p>
     * This method it's used by Administrator when he needs to look up
     * all professors from a specified course
     * </p>
     * @return List<Professor> a list with all professors from specified course
     */
    @Override
    public List<Professor> getAllProfessorsByCourseID(int idCourse) {
        List<Professor> professors = new ArrayList<>();
        try(Connection conn = database.getConnection() ) {
            String statement = "SELECT PROF.id_person, PROF.cubicle, PROF.staff_number, PERSPROF.name, PERSPROF.phoneNumber, PERSPROF.email, PERSPROF.activity_state, COUR.id_course, COUR.NRC, COUR.period, COUR.name FROM Professor AS PROF INNER JOIN PERSON AS PERSPROF ON PROF.id_person = PERSPROF.id_person INNER JOIN COURSE AS COUR ON PERSPROF.id_course = COUR.id_course AND COUR.id_course = ?";
            PreparedStatement queryProfessors = conn.prepareStatement(statement);
            queryProfessors.setInt(1, idCourse);
            resultSet = queryProfessors.executeQuery();
            while( resultSet.next() ) {
                Course course = new Course();
                course.setId( resultSet.getInt("COUR.id_course") );
                course.setName( resultSet.getString("COUR.name") );
                course.setNRC( resultSet.getString("COUR.NRC") );
                course.setPeriod( resultSet.getString("COUR.period") );
                Professor professor = new Professor();
                professor.setName( resultSet.getString("PERSPROF.name") );
                professor.setPhoneNumber( resultSet.getString("PERSPROF.phoneNumber") );
                professor.setEmail( resultSet.getString("PERSPROF.email") );
                professor.setActivityState( resultSet.getString("PERSPROF.activity_state") );
                professor.setId( resultSet.getInt("PROF.id_person") );
                professor.setCubicle( resultSet.getInt("PROF.cubicle") );
                professor.setStaffNumber( resultSet.getString("PROF.staff_number") );
                professor.setCourse(course);
                professors.add(professor);
            }
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger( ProfessorDAO.class.getName() ).log(Level.WARNING, null, e);
        }
        return professors;
    }

    /***
     * Get all professors by every course.
     * <p>
     * This method return all the professors available in the system.
     * The purpose it's their management.
     * </p>
     * @return List<Professor> a list with all professors from specified course
     */
    @Override
    public List<Professor> getAllProfessors() {
        List<Professor> professors = new ArrayList<>();
        try(Connection conn = database.getConnection() ) {
            String statement = "SELECT PROF.id_person, PROF.cubicle, PROF.staff_number, PERSPROF.name, PERSPROF.phoneNumber, PERSPROF.email, PERSPROF.activity_state, COUR.id_course, COUR.NRC, COUR.period, COUR.name FROM Professor AS PROF INNER JOIN PERSON AS PERSPROF ON PROF.id_person = PERSPROF.id_person INNER JOIN COURSE AS COUR ON PERSPROF.id_course = COUR.id_course";
            PreparedStatement queryProfessors = conn.prepareStatement(statement);
            resultSet = queryProfessors.executeQuery();
            while( resultSet.next() ) {
                Course course = new Course();
                course.setId( resultSet.getInt("COUR.id_course") );
                course.setName( resultSet.getString("COUR.name") );
                course.setNRC( resultSet.getString("COUR.NRC") );
                course.setPeriod( resultSet.getString("COUR.period") );
                Professor professor = new Professor();
                professor.setName( resultSet.getString("PERSPROF.name") );
                professor.setPhoneNumber( resultSet.getString("PERSPROF.phoneNumber") );
                professor.setEmail( resultSet.getString("PERSPROF.email") );
                professor.setActivityState( resultSet.getString("PERSPROF.activity_state") );
                professor.setId( resultSet.getInt("PROF.id_person") );
                professor.setCubicle( resultSet.getInt("PROF.cubicle") );
                professor.setStaffNumber( resultSet.getString("PROF.staff_number") );
                professor.setCourse(course);
                professors.add(professor);
            }
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger( ProfessorDAO.class.getName() ).log(Level.WARNING, null, e);
        }
        return professors;
    }

}
