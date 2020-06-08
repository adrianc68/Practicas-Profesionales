package org.database.dao;

import org.domain.Course;

import java.sql.SQLException;
import java.util.List;

public interface ICourseDAO {
    /***
     * Add a new educational experiencia related to professional practices
     * <p>
     * This method is used by Administrator when he is going to start a new course.
     * </p>
     * @param course
     * @return int representing course's id
     */
    int addCourse(Course course) throws SQLException;
    /***
     * Remove a course related to professional practices
     * <p>
     * This method is used by Administrator when he needs to remove a course by any reason.
     * </p>
     * @param idCourse
     * @return true if course was removed from database.
     */
    boolean removeCourseByID(int idCourse) throws SQLException;

    /***
     * Get the last course.
     * <p>
     * This method is used by any user from the system when they need it for any reason.
     * </p>
     * @return
     */
    Course getLastCourse() throws SQLException;

    /***
     * Get all the courses from the system.
     * <p>
     * This method is used by the administrator when he needs to look up some course.
     * </p>
     * @return List<Course> a list containing all the courses
     */
    List<Course> getAllCourses() throws SQLException;

}
