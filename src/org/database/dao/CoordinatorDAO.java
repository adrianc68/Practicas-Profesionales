package org.database.dao;

import org.database.Database;
import org.domain.ActivityState;
import org.domain.Coordinator;
import org.domain.Course;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoordinatorDAO implements ICoordinatorDAO{
    private final Database database;

    /***
     * CoordinatorDAO constructor.
     * This constructor initialize a connection to the database.
     */
    public CoordinatorDAO() {
        this.database = new Database();
    }

    /***
     * Add a new coordinator to Course.
     * <p>
     * This method is used by Administrator when he needs someone to
     * manage the course
     * </p>
     * @param coordinator the coordinator to be added to database.
     * @return int representing the coordinator's id.
     */
    @Override
    public int addCoordinator(Coordinator coordinator) throws SQLException {
        int idCoordinator = 0;
        try(Connection conn = database.getConnection() ){
            conn.setAutoCommit(false);
            String statement = "CALL addCoordinator(?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setString( 1, coordinator.getName() );
            preparedStatement.setString(2, coordinator.getPhoneNumber() );
            preparedStatement.setString(3, coordinator.getEmail() );
            preparedStatement.setInt(4, coordinator.getCourse().getId() );
            preparedStatement.setInt(5, coordinator.getCubicle() );
            preparedStatement.setString(6, coordinator.getStaffNumber() );
            preparedStatement.executeUpdate();
            statement = "SELECT LAST_INSERT_ID()";
            preparedStatement = conn.prepareStatement(statement);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            idCoordinator = resultSet.getInt(1);
            conn.commit();
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return idCoordinator;
    }

    /***
     * Remove a coordiantor from course.
     * <p>
     * This method is used by Administrator when he needs to remove a coordinator
     * for any reason.
     * </p>
     * @param idCoordinator coordinator's id to be removed.
     * @return boolean true if coordinator was deleted from database.
     */
    @Override
    public boolean removeCoordinatorByID(int idCoordinator) throws SQLException {
        int rowsAffected = 0;
        try(Connection conn = database.getConnection() ){
            conn.setAutoCommit(false);
            String statement = "DELETE FROM Person WHERE id_person = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setInt( 1, idCoordinator );
            rowsAffected = preparedStatement.executeUpdate();
            conn.commit();
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return rowsAffected > 0;
    }

    /***
     * Get all coordinators from current course.
     * <p>
     * This method gets all coordinator from a specified course. It's goal is
     * management.
     * </p>
     * @return List<Course> a list containing all coordinators.
     */
    @Override
    public List<Coordinator> getAllCoordinatorsByCourseID(int idCourse) throws SQLException {
        List<Coordinator> coordinators = new ArrayList<>();
        try(Connection conn = database.getConnection() ){
            conn.setAutoCommit(false);
            String statement = "SELECT CORD.id_person, CORD.cubicle, CORD.staff_number, PERSCORD.name, PERSCORD.phoneNumber, PERSCORD.email, PERSCORD.activity_state, COUR.id_course, COUR.NRC, COUR.period, COUR.name FROM Coordinator AS CORD INNER JOIN Person AS PERSCORD ON PERSCORD.id_person = CORD.id_person INNER JOIN Course AS COUR ON PERSCORD.id_course = COUR.id_course AND COUR.id_course = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setInt(1, idCourse);
            ResultSet resultSet = preparedStatement.executeQuery();
            while( resultSet.next() ) {
                Course course = new Course();
                course.setName( resultSet.getString("COUR.name") );
                course.setPeriod( resultSet.getString("COUR.period") );
                course.setNRC( resultSet.getString("COUR.NRC") );
                course.setId( resultSet.getInt("COUR.id_course") );
                Coordinator coordinator = new Coordinator();
                coordinator.setName( resultSet.getString("PERSCORD.name") );
                coordinator.setPhoneNumber( resultSet.getString("PERSCORD.phoneNumber") );
                coordinator.setEmail( resultSet.getString("PERSCORD.email") );
                coordinator.setCubicle( resultSet.getInt("CORD.cubicle") );
                coordinator.setStaffNumber( resultSet.getString("CORD.staff_number") );
                coordinator.setId( resultSet.getInt("CORD.id_person") );
                coordinator.setActivityState( ActivityState.valueOf( resultSet.getString("PERSCORD.activity_state").toUpperCase() ) );
                coordinator.setCourse(course);
                coordinators.add(coordinator);
            }
            conn.commit();
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return coordinators;
    }

    /***
     * Get all coordinators from every course
     * <p>
     * This method gets all coordinator from every course. It's goal is
     * management.
     * </p>
     * @return List<Course> a list containing all coordinators.
     */
    @Override
    public List<Coordinator> getAllCoordinators() throws SQLException {
        List<Coordinator> coordinators = new ArrayList<>();
        try(Connection conn = database.getConnection() ){
            conn.setAutoCommit(false);
            String statement = "SELECT CORD.id_person, CORD.cubicle, CORD.staff_number, PERSCORD.name, PERSCORD.phoneNumber, PERSCORD.email, PERSCORD.activity_state, COUR.id_course, COUR.NRC, COUR.period, COUR.name FROM Coordinator AS CORD INNER JOIN Person AS PERSCORD ON PERSCORD.id_person = CORD.id_person INNER JOIN Course AS COUR ON PERSCORD.id_course = COUR.id_course";
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while( resultSet.next() ) {
                Course course = new Course();
                course.setName( resultSet.getString("COUR.name") );
                course.setPeriod( resultSet.getString("COUR.period") );
                course.setNRC( resultSet.getString("COUR.NRC") );
                course.setId( resultSet.getInt("COUR.id_course") );
                Coordinator coordinator = new Coordinator();
                coordinator.setName( resultSet.getString("PERSCORD.name") );
                coordinator.setPhoneNumber( resultSet.getString("PERSCORD.phoneNumber") );
                coordinator.setEmail( resultSet.getString("PERSCORD.email") );
                coordinator.setActivityState( ActivityState.valueOf( resultSet.getString("PERSCORD.activity_state").toUpperCase() ) );
                coordinator.setCubicle( resultSet.getInt("CORD.cubicle") );
                coordinator.setStaffNumber( resultSet.getString("CORD.staff_number") );
                coordinator.setId( resultSet.getInt("CORD.id_person") );
                coordinator.setCourse(course);
                coordinators.add(coordinator);
            }
            conn.commit();
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return coordinators;
    }

}
