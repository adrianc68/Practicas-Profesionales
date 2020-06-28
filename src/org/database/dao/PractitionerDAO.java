package org.database.dao;

import org.database.Database;
import org.domain.ActivityState;
import org.domain.Course;
import org.domain.Delivery;
import org.domain.Practitioner;
import org.domain.Project;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PractitionerDAO implements IPractitionerDAO {
    private final Database database;

    /***
     * PractitionerDAO constructor.
     * This constructor initialize a connection to the database.
     */
    public PractitionerDAO() {
        database = new Database();
    }

    /***
     * Add a Practitioner to database.
     * <p>
     * This method is used by the coordinator when he needs to add a practicing.
     * </p>
     * @param practitioner to be added to database
     * @return int the practitioner's id
     */
    @Override
    public int addPractitioner(Practitioner practitioner) throws SQLException {
        int idPractitioner = 0;
        try(Connection conn = database.getConnection()){
            conn.setAutoCommit(false);
            String statement = "CALL addPractitioner(?, ?, ?, ?, ?)";
            PreparedStatement addPractitioner = conn.prepareStatement(statement);
            addPractitioner.setString(1, practitioner.getName() );
            addPractitioner.setString(2, practitioner.getPhoneNumber() );
            addPractitioner.setString(3, practitioner.getEmail() );
            addPractitioner.setInt(4, practitioner.getCourse().getId() );
            addPractitioner.setString(5, practitioner.getEnrollment() );
            addPractitioner.executeUpdate();
            statement = "SELECT LAST_INSERT_ID()";
            addPractitioner = conn.prepareStatement(statement);
            ResultSet result = addPractitioner.executeQuery();
            result.next();
            idPractitioner = result.getInt(1);
            conn.commit();
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return idPractitioner;
    }

    /***
     * Delete a Practitioner from database.
     * <p>
     * It's used by the coordinator whem he needs to remove a practicing for any reason.
     * </p>
     * @param idPractitioner the practitioner's ID
     * @return boolean true if 1 o more than 1 rows are affected
     */
    @Override
    public boolean removePractitioner(int idPractitioner) throws SQLException {
        int rowsAffected = 0;
        try(Connection conn = database.getConnection()){
            conn.setAutoCommit(false);
            String statement = "DELETE FROM Practitioner WHERE id_person = ?";
            PreparedStatement removePractitioner = conn.prepareStatement(statement);
            removePractitioner.setInt(1, idPractitioner );
            rowsAffected = removePractitioner.executeUpdate();
            conn.commit();
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return rowsAffected > 0;
    }

    /***
     * Get a Practitioner by a specific delivery.
     * <p>
     * This method it's used by Professor when he needs to look a delivery.
     * </p>
     * @param idDelivery to get the practitioner
     * @return Practitioner
     * @throws SQLException
     */
    @Override
    public Practitioner getPractitionerByDelivery(int idDelivery) throws SQLException {
        Practitioner practitioner = null;
        try(Connection conn = database.getConnection() ) {
            String statement = "SELECT PERS.name, PERS.phoneNumber, PERS.email, PERS.activity_state, PRAC.id_person,PRAC.enrollment, COUR.id_course, COUR.NRC, COUR.name, COUR.period FROM Delivery AS DEL INNER JOIN Person AS PERS ON PERS.id_person = DEL.id_practitioner and DEL.id_delivery = ? INNER JOIN Course AS COUR ON COUR.id_course = PERS.id_course INNER JOIN Practitioner AS PRAC ON PRAC.id_person = DEL.id_practitioner";
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setInt(1, idDelivery);
            ResultSet resultSet = preparedStatement.executeQuery();
            if( resultSet.next() ) {
                Course course = new Course();
                course.setId( resultSet.getInt("COUR.id_course") ) ;
                course.setName( resultSet.getString("COUR.name") );
                course.setNRC( resultSet.getString("COUR.NRC") );
                course.setPeriod( resultSet.getString("COUR.period") );
                practitioner = new Practitioner();
                practitioner.setName( resultSet.getString("PERS.name") );
                practitioner.setPhoneNumber( resultSet.getString("PERS.phoneNumber") );
                practitioner.setEmail( resultSet.getString("PERS.email") );
                practitioner.setActivityState( ActivityState.valueOf( resultSet.getString("PERS.activity_state").toUpperCase() ) );
                practitioner.setId( resultSet.getInt("PRAC.id_person") );
                practitioner.setEnrollment( resultSet.getString("PRAC.enrollment") );
                practitioner.setCourse(course);
                practitioner.setProfessor(null);
                practitioner.setProject(null);
            }
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return practitioner;
    }

    /***
     * Get all practitioners and their assigned projects from the actual/last course
     * <p>
     * This method is used by the coordinator and professor.
     * Doesn't return with any activity delivery, selected project, assigned project or assigned professor
     * </p>
     * @return List<Practitioner> a list with practitioners of the actual/last course from database
     */
    @Override
    public List<Practitioner> getAllPractitionersFromLastCourse() throws SQLException {
        List<Practitioner> practitioners = new ArrayList<>();
        try(Connection conn = database.getConnection() ) {
            String statement = "SELECT PRAC.id_person, PRAC.enrollment, PERS.name, PERS.phoneNumber, PERS.email, PERS.activity_state, COUR.id_course, COUR.NRC, COUR.period, COUR.name FROM Practitioner AS PRAC INNER JOIN Person AS PERS ON PRAC.id_person = PERS.id_person INNER JOIN Course AS COUR ON PERS.id_course = COUR.id_course AND COUR.id_course = (SELECT max(id_course) FROM Course)";
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while( resultSet.next() ) {
                Course course = new Course();
                course.setId( resultSet.getInt("COUR.id_course") ) ;
                course.setName( resultSet.getString("COUR.name") );
                course.setNRC( resultSet.getString("COUR.NRC") );
                course.setPeriod( resultSet.getString("COUR.period") );
                Practitioner practitioner = new Practitioner();
                practitioner.setName( resultSet.getString("PERS.name") );
                practitioner.setPhoneNumber( resultSet.getString("PERS.phoneNumber") );
                practitioner.setEmail( resultSet.getString("PERS.email") );
                practitioner.setActivityState( ActivityState.valueOf( resultSet.getString("PERS.activity_state").toUpperCase() ) );
                practitioner.setId( resultSet.getInt("PRAC.id_person") );
                practitioner.setEnrollment( resultSet.getString("PRAC.enrollment") );
                practitioner.setCourse(course);
                List<Project> selectedProjects = new ArrayList<>();
                List<Delivery> deliveries = new ArrayList<>();
                practitioner.setSelectedProjects(selectedProjects);
                practitioner.setDeliveries(deliveries);
                practitioner.setProfessor(null);
                practitioner.setProject(null);
                practitioners.add(practitioner);
            }
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return practitioners;
    }

    /***
     * Get all practitioners by a Professor
     * <p>
     * This method is used by the the professor when he needs to look
     * who is assigned to him.
     * </p>
     * @return List<Practitioner> a list with practitioners assigned to professor.
     */
    public List<Practitioner> getAllPractitionersByProfessor(int idProfessor) throws SQLException {
        List<Practitioner> practitioners = new ArrayList<>();
        try(Connection conn = database.getConnection() ) {
            String statement = "SELECT PRAC.id_person, PRAC.enrollment, PERSPRAC.name, PERSPRAC.phoneNumber, PERSPRAC.email, PERSPRAC.activity_state, COUR.id_course, COUR.NRC, COUR.period, COUR.name from Practitioner AS PRAC INNER JOIN Professor AS PROF ON PRAC.id_professor = PROF.id_person AND PROF.id_person = ? INNER JOIN Person AS PERSPRAC ON PERSPRAC.id_person = PRAC.id_person INNER JOIN Course AS COUR ON PERSPRAC.id_course = COUR.id_course";
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setInt(1, idProfessor);
            ResultSet resultSet = preparedStatement.executeQuery();
            while( resultSet.next() ) {
                Course course = new Course();
                course.setId( resultSet.getInt("COUR.id_course") ) ;
                course.setName( resultSet.getString("COUR.name") );
                course.setNRC( resultSet.getString("COUR.NRC") );
                course.setPeriod( resultSet.getString("COUR.period") );
                Practitioner practitioner = new Practitioner();
                practitioner.setName( resultSet.getString("PERSPRAC.name") );
                practitioner.setPhoneNumber( resultSet.getString("PERSPRAC.phoneNumber") );
                practitioner.setEmail( resultSet.getString("PERSPRAC.email") );
                practitioner.setActivityState( ActivityState.valueOf( resultSet.getString("PERSPRAC.activity_state").toUpperCase() ) );
                practitioner.setId( resultSet.getInt("PRAC.id_person") );
                practitioner.setEnrollment( resultSet.getString("PRAC.enrollment") );
                practitioner.setCourse(course);
                List<Project> selectedProjects = new ArrayList<>();
                List<Delivery> deliveries = new ArrayList<>();
                practitioner.setSelectedProjects(selectedProjects);
                practitioner.setDeliveries(deliveries);
                practitioner.setProfessor(null);
                practitioner.setProject(null);
                practitioners.add(practitioner);
            }
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return practitioners;
    }

}
