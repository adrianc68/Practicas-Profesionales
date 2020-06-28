package org.database.dao;

import org.database.Database;
import org.domain.ActivityState;
import org.domain.Course;
import org.domain.Professor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO implements IProfessorDAO {
    private final Database database;

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
    public int addProfessor(Professor professor) throws SQLException {
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
            ResultSet resultSet = insertProfessor.executeQuery();
            resultSet.next();
            idProfessor = resultSet.getInt(1);
            conn.commit();
        } catch (SQLException sqlException) {
            throw sqlException;
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
    public boolean removeProfessorByID(int idProfessor) throws SQLException {
        int rowsAffected = 0;
        try(Connection conn = database.getConnection() ){
            conn.setAutoCommit(false);
            String statement = "DELETE FROM Professor WHERE id_person = ?";
            PreparedStatement deleteProfessor = conn.prepareStatement(statement);
            deleteProfessor.setInt( 1, idProfessor );
            rowsAffected = deleteProfessor.executeUpdate();
            conn.commit();
        } catch (SQLException sqlException) {
           throw sqlException;
        }
        return rowsAffected > 0;
    }

    /***
     * Assign a professor to a Practitioner in the database.
     * <p>
     * It's used by the coordinator when he decided assign a professor to a practitioner.
     * </p>
     * @param idPractitioner the practitioner's ID
     * @param idProfessor the professor's id to be assigned to practitioner
     * @return boolean true if 1 o more than 1 rows are affected
     */
    @Override
    public boolean assignProfessorToPractitioner(int idPractitioner, int idProfessor) throws SQLException {
        int rowsAffected = 0;
        try(Connection conn = database.getConnection()){
            conn.setAutoCommit(false);
            String statement = "UPDATE Practitioner SET id_professor = ? WHERE id_person = ?";
            PreparedStatement assignProfessor = conn.prepareStatement(statement);
            assignProfessor.setInt(1, idProfessor );
            assignProfessor.setInt(2, idPractitioner );
            rowsAffected = assignProfessor.executeUpdate();
            conn.commit();
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return  rowsAffected > 0;
    }

    /***
     * Delete a assign of professor to a Practitioner in the database.
     * <p>
     * It's used by the coordinator when he decided to remove a assign of professor to a practitioner.
     * </p>
     * @param idPractitioner the practitioner's ID
     * @return boolean true if 1 o more than 1 rows are affected
     */
    @Override
    public boolean removeAssignedProfessorToPractitioner(int idPractitioner) throws SQLException {
        int rowsAffected = 0;
        try(Connection conn = database.getConnection()){
            conn.setAutoCommit(false);
            String statement = "UPDATE Practitioner SET id_professor = NULL WHERE id_person = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setInt(1, idPractitioner );
            rowsAffected = preparedStatement.executeUpdate();
            conn.commit();
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return  rowsAffected > 0;
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
    public Professor getAssignedProfessorByPractitionerID(int idPractitioner) throws SQLException {
        Professor professor = null;
        try(Connection conn = database.getConnection() ) {
            String statement = "SELECT PROF.id_person, PROF.cubicle, PROF.staff_number, PERSPROF.name, PERSPROF.phoneNumber, PERSPROF.email, PERSPROF.activity_state, COUR.id_course, COUR.NRC, COUR.period, COUR.name FROM Professor AS PROF INNER JOIN Practitioner AS PRAC ON PROF.id_person = PRAC.id_professor AND PRAC.id_person = ? INNER JOIN Person AS PERSPROF ON PROF.id_person = PERSPROF.id_person INNER JOIN Course AS COUR ON PERSPROF.id_course = COUR.id_course";
            PreparedStatement queryProfessor = conn.prepareStatement(statement);
            queryProfessor.setInt(1, idPractitioner);
            ResultSet resultSet = queryProfessor.executeQuery();
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
                professor.setActivityState(  ActivityState.valueOf( resultSet.getString("PERSPROF.activity_state").toUpperCase() )  );
                professor.setId( resultSet.getInt("PROF.id_person") );
                professor.setCubicle( resultSet.getInt("PROF.cubicle") );
                professor.setStaffNumber( resultSet.getString("PROF.staff_number") );
                professor.setCourse(course);
            }
        } catch (SQLException sqlException) {
            throw sqlException;
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
    public List<Professor> getAllProfessorsFromLastCourse() throws SQLException {
        List<Professor> professors = new ArrayList<>();
        try(Connection conn = database.getConnection() ) {
            String statement = "SELECT PROF.id_person, PROF.cubicle, PROF.staff_number, PERSPROF.name, PERSPROF.phoneNumber, PERSPROF.email, PERSPROF.activity_state, COUR.id_course, COUR.NRC, COUR.period, COUR.name FROM Professor AS PROF INNER JOIN Person AS PERSPROF ON PROF.id_person = PERSPROF.id_person INNER JOIN Course AS COUR ON PERSPROF.id_course = COUR.id_course AND COUR.id_course = (SELECT max(id_course) FROM Course)";
            PreparedStatement queryProfessors = conn.prepareStatement(statement);
            ResultSet resultSet = queryProfessors.executeQuery();
            while( resultSet.next() ) {
                Course course = new Course();
                course.setId( resultSet.getInt("COUR.id_course") );
                course.setName( resultSet.getString("COUR.name") );
                course.setNRC( resultSet.getString("COUR.NRC") );
                course.setPeriod( resultSet.getString("COUR.period") );
                Professor professor = new Professor();
                professor.setName( resultSet.getString("PERSPROF.name") );
                professor.setPhoneNumber( resultSet.getString("PERSPROF.phoneNumber") );
                professor.setActivityState( ActivityState.valueOf( resultSet.getString("PERSPROF.activity_state").toUpperCase() ) );
                professor.setEmail( resultSet.getString("PERSPROF.email") );
                professor.setId( resultSet.getInt("PROF.id_person") );
                professor.setCubicle( resultSet.getInt("PROF.cubicle") );
                professor.setStaffNumber( resultSet.getString("PROF.staff_number") );
                professor.setCourse(course);
                professors.add(professor);
            }
        } catch (SQLException sqlException) {
            throw sqlException;
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
    public List<Professor> getAllProfessorsByCourseID(int idCourse) throws SQLException {
        List<Professor> professors = new ArrayList<>();
        try(Connection conn = database.getConnection() ) {
            String statement = "SELECT PROF.id_person, PROF.cubicle, PROF.staff_number, PERSPROF.name, PERSPROF.phoneNumber, PERSPROF.email, PERSPROF.activity_state, COUR.id_course, COUR.NRC, COUR.period, COUR.name FROM Professor AS PROF INNER JOIN Person AS PERSPROF ON PROF.id_person = PERSPROF.id_person INNER JOIN Course AS COUR ON PERSPROF.id_course = COUR.id_course AND COUR.id_course = ?";
            PreparedStatement queryProfessors = conn.prepareStatement(statement);
            queryProfessors.setInt(1, idCourse);
            ResultSet resultSet = queryProfessors.executeQuery();
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
                professor.setActivityState( ActivityState.valueOf( resultSet.getString("PERSPROF.activity_state").toUpperCase() ) );
                professor.setId( resultSet.getInt("PROF.id_person") );
                professor.setCubicle( resultSet.getInt("PROF.cubicle") );
                professor.setStaffNumber( resultSet.getString("PROF.staff_number") );
                professor.setCourse(course);
                professors.add(professor);
            }
        } catch (SQLException sqlException) {
            throw sqlException;
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
    public List<Professor> getAllProfessors() throws SQLException {
        List<Professor> professors = new ArrayList<>();
        try(Connection conn = database.getConnection() ) {
            String statement = "SELECT PROF.id_person, PROF.cubicle, PROF.staff_number, PERSPROF.name, PERSPROF.phoneNumber, PERSPROF.email, PERSPROF.activity_state, COUR.id_course, COUR.NRC, COUR.period, COUR.name FROM Professor AS PROF INNER JOIN Person AS PERSPROF ON PROF.id_person = PERSPROF.id_person INNER JOIN Course AS COUR ON PERSPROF.id_course = COUR.id_course";
            PreparedStatement queryProfessors = conn.prepareStatement(statement);
            ResultSet resultSet = queryProfessors.executeQuery();
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
                professor.setActivityState( ActivityState.valueOf( resultSet.getString("PERSPROF.activity_state").toUpperCase() ) );
                professor.setId( resultSet.getInt("PROF.id_person") );
                professor.setCubicle( resultSet.getInt("PROF.cubicle") );
                professor.setStaffNumber( resultSet.getString("PROF.staff_number") );
                professor.setCourse(course);
                professors.add(professor);
            }
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return professors;
    }

}
