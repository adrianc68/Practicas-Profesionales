package test.org.database.dao;

import org.database.dao.CourseDAO;
import org.domain.Course;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseDAOTest {

    @Test
    void addCourse() {
    }

    @Test
    void removeCourseByID() {

    }

    @Test
    void getLastCourse() {
        int expectedID = 1;
        CourseDAO courseDAO = new CourseDAO();
        Course course = courseDAO.getLastCourse();
        int actualID = course.getId();
        assertEquals(expectedID, actualID);
    }

    @Test
    void getAllCourses() {

    }
}