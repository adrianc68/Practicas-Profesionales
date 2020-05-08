package org.database.dao;

import org.database.Database;
import org.domain.Course;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CourseDAO implements ICourseDAO {
    private final Database database;
    private ResultSet resultSet;

    /***
     * CourseDAO constructor.
     * This constructor initialize a connection to the database.
     */
    public CourseDAO() {
        this.database = new Database();
    }

    /***
     * Add a new educational experiencia related to professional practices
     * <p>
     * This method is used by Administrator when he is going to start a new course.
     * </p>
     * @param course
     * @return int representing course's id
     */
    @Override
    public int addCourse(Course course) {
        int idCourse = 0;
        try(Connection conn = database.getConnection() ){
            conn.setAutoCommit(false);
            String statement = "INSERT INTO COURSE(NRC, period, name) VALUES(?,?,?)";
            PreparedStatement insertCourse = conn.prepareStatement(statement);
            insertCourse.setString( 1, course.getNRC() );
            insertCourse.setString(2, course.getPeriod() );
            insertCourse.setString(3, course.getName() );
            insertCourse.executeUpdate();
            statement = "SELECT LAST_INSERT_ID()";
            insertCourse = conn.prepareStatement(statement);
            resultSet = insertCourse.executeQuery();
            resultSet.next();
            idCourse = resultSet.getInt(1);
            conn.commit();
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.WARNING, null, e);
        }
        return idCourse;
    }

    /***
     * Remove a course related to professional practices
     * <p>
     * This method is used by Administrator when he needs to remove a course by any reason.
     * </p>
     * @param idCourse
     * @return true if course was removed from database.
     */
    @Override
    public boolean removeCourseByID(int idCourse) {
        int rowsAffected = 0;
        try(Connection conn = database.getConnection() ){
            conn.setAutoCommit(false);
            String statement = "DELETE FROM COURSE WHERE id_course = ?";
            PreparedStatement deleteCourse = conn.prepareStatement(statement);
            deleteCourse.setInt( 1, idCourse );
            rowsAffected = deleteCourse.executeUpdate();
            conn.commit();
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.WARNING, null, e);
        }
        return rowsAffected > 0;
    }

    /***
     * Get the last course.
     * <p>
     * This method is used by any user from the system when they need it for any reason.
     * </p>
     * @return
     */
    @Override
    public Course getLastCourse() {
        Course course = null;
        try(Connection conn = database.getConnection() ){
            conn.setAutoCommit(false);
            String statement = "SELECT COUR.id_course, COUR.NRC, COUR.period, COUR.name FROM Course AS COUR WHERE COUR.id_course = (SELECT max(id_course) FROM COURSE)";
            PreparedStatement queryCourse = conn.prepareStatement(statement);
            resultSet = queryCourse.executeQuery();
            resultSet.next();
            course = new Course();
            course.setName(resultSet.getString("COUR.name"));
            course.setPeriod(resultSet.getString("COUR.period"));
            course.setNRC(resultSet.getString("COUR.NRC"));
            course.setId(resultSet.getInt("COUR.id_course"));
            conn.commit();
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.WARNING, null, e);
        }
        return course;
    }

    /***
     * Get all the courses from the system.
     * <p>
     * This method is used by the administrator when he needs to look up some course.
     * </p>
     * @return List<Course> a list containing all the courses
     */
    @Override
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        try(Connection conn = database.getConnection() ){
            conn.setAutoCommit(false);
            String statement = "SELECT COUR.id_course, COUR.NRC, COUR.period, COUR.name FROM Course AS COUR";
            PreparedStatement queryCourse = conn.prepareStatement(statement);
            resultSet = queryCourse.executeQuery();
            while( resultSet.next() ) {
                Course course = new Course();
                course.setName(resultSet.getString("COUR.name"));
                course.setPeriod(resultSet.getString("COUR.period"));
                course.setNRC(resultSet.getString("COUR.NRC"));
                course.setId(resultSet.getInt("COUR.id_course"));
                courses.add(course);
            }
            conn.commit();
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.WARNING, null, e);
        }
        return courses;
    }
}
