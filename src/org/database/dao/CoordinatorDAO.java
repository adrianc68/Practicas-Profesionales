package org.database.dao;

import org.database.Database;
import org.domain.Coordinator;
import org.domain.Course;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CoordinatorDAO implements ICoordinatorDAO{
    private final Database database;
    private ResultSet resultSet;

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
    public int addCoordinator(Coordinator coordinator) {
        int idCoordinator = 0;
        try(Connection conn = database.getConnection() ){
            conn.setAutoCommit(false);
            String statement = "CALL addCoordinator(?, ?, ?, ?, ?, ?)";
            PreparedStatement insertCoordinator = conn.prepareStatement(statement);
            insertCoordinator.setString( 1, coordinator.getName() );
            insertCoordinator.setString(2, coordinator.getPhoneNumber() );
            insertCoordinator.setString(3, coordinator.getEmail() );
            insertCoordinator.setInt(4, coordinator.getCourse().getId() );
            insertCoordinator.setInt(5, coordinator.getCubicle() );
            insertCoordinator.setString(6, coordinator.getStaffNumber() );
            insertCoordinator.executeUpdate();
            statement = "SELECT LAST_INSERT_ID()";
            insertCoordinator = conn.prepareStatement(statement);
            resultSet = insertCoordinator.executeQuery();
            resultSet.next();
            idCoordinator = resultSet.getInt(1);
            conn.commit();
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger( CoordinatorDAO.class.getName() ).log(Level.WARNING, null, e);
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
    public boolean removeCoordinatorByID(int idCoordinator) {
        int rowsAffected = 0;
        try(Connection conn = database.getConnection() ){
            conn.setAutoCommit(false);
            String statement = "DELETE FROM Person WHERE id_person = ?";
            PreparedStatement deleteCoordinator = conn.prepareStatement(statement);
            deleteCoordinator.setInt( 1, idCoordinator );
            rowsAffected = deleteCoordinator.executeUpdate();
            conn.commit();
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger( CoordinatorDAO.class.getName() ).log(Level.WARNING, null, e);
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
    public List<Coordinator> getAllCoordinatorsByCourseID(int idCourse) {
        List<Coordinator> coordinators = new ArrayList<>();
        try(Connection conn = database.getConnection() ){
            conn.setAutoCommit(false);
            String statement = "SELECT CORD.id_person, CORD.cubicle, CORD.staff_number, PERSCORD.name, PERSCORD.phoneNumber, PERSCORD.email, PERSCORD.activity_state, COUR.id_course, COUR.NRC, COUR.period, COUR.name FROM Coordinator AS CORD INNER JOIN PERSON AS PERSCORD ON PERSCORD.id_person = CORD.id_person INNER JOIN COURSE AS COUR ON PERSCORD.id_course = COUR.id_course AND COUR.id_course = ?";
            PreparedStatement queryCoordinator = conn.prepareStatement(statement);
            queryCoordinator.setInt(1, idCourse);
            resultSet = queryCoordinator.executeQuery();
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
                coordinator.setActivityState( resultSet.getString("PERSCORD.activity_state") );
                coordinator.setCourse(course);
                coordinators.add(coordinator);
            }
            conn.commit();
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger( CoordinatorDAO.class.getName() ).log(Level.WARNING, null, e);
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
    public List<Coordinator> getAllCoordinators() {
        List<Coordinator> coordinators = new ArrayList<>();
        try(Connection conn = database.getConnection() ){
            conn.setAutoCommit(false);
            String statement = "SELECT CORD.id_person, CORD.cubicle, CORD.staff_number, PERSCORD.name, PERSCORD.phoneNumber, PERSCORD.email, PERSCORD.activity_state, COUR.id_course, COUR.NRC, COUR.period, COUR.name FROM Coordinator AS CORD INNER JOIN PERSON AS PERSCORD ON PERSCORD.id_person = CORD.id_person INNER JOIN COURSE AS COUR ON PERSCORD.id_course = COUR.id_course";
            PreparedStatement queryCoordinator = conn.prepareStatement(statement);
            resultSet = queryCoordinator.executeQuery();
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
                coordinator.setActivityState( resultSet.getString("PERSCORD.activity_state") );
                coordinator.setCubicle( resultSet.getInt("CORD.cubicle") );
                coordinator.setStaffNumber( resultSet.getString("CORD.staff_number") );
                coordinator.setId( resultSet.getInt("CORD.id_person") );
                coordinator.setCourse(course);
                coordinators.add(coordinator);
            }
            conn.commit();
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger( CoordinatorDAO.class.getName() ).log(Level.WARNING, null, e);
        }
        return coordinators;
    }

}
