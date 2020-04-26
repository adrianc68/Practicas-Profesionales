package org.database.dao;

import org.database.Database;
import org.domain.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CourseDAO implements ICourseDAO {
    private final Database database;
    private ResultSet resultSet;

    public CourseDAO() {
        this.database = new Database();
    }

    @Override
    public boolean addCourse(Course course) {
        return true;
    }

    @Override
    public boolean removeCourseByID(int idCourse) {
        return true;
    }

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

    @Override
    public List<Course> getAllCourses() {
        return null;
    }
}
