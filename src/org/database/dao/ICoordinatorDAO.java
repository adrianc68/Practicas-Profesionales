package org.database.dao;

import org.domain.Coordinator;

import java.sql.SQLException;
import java.util.List;

public interface ICoordinatorDAO {
    /***
     * Add a new coordinator to Course.
     * <p>
     * This method is used by Administrator when he needs someone to
     * manage the course
     * </p>
     * @param coordinator the coordinator to be added to database.
     * @return int representing the coordinator's id.
     */
    int addCoordinator(Coordinator coordinator) throws SQLException;

    /***
     * Remove a coordiantor from course.
     * <p>
     * This method is used by Administrator when he needs to remove a coordinator
     * for any reason.
     * </p>
     * @param idCoordinator coordinator's id to be removed.
     * @return boolean true if coordinator was deleted from database.
     */
    boolean removeCoordinatorByID(int idCoordinator) throws SQLException;

    /***
     * Get all coordinators from current course.
     * <p>
     * This method gets all coordinator from a specified course. It's goal is
     * management.
     * </p>
     * @return List<Course> a list containing all coordinators.
     */
    List<Coordinator> getAllCoordinatorsByCourseID(int idCourse) throws SQLException;

    /***
     * Get all coordinators from every course
     * <p>
     * This method gets all coordinator from every course. It's goal is
     * management.
     * </p>
     * @return List<Course> a list containing all coordinators.
     */
    List<Coordinator> getAllCoordinators() throws SQLException;
}
