package test.org.database.dao;

import org.database.dao.CourseDAO;
import org.domain.Course;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class CourseDAOTest {
    private int idCourse = 0;

    @Test
    void addCourse() {
        Course course = new Course();
        course.setName("Practicas Profesionales");
        course.setNRC("N192127");
        course.setPeriod("AGO 2020 - ENE 2021");
        CourseDAO courseDAO = new CourseDAO();
        int actual = courseDAO.addCourse(course);
        int unexpected = 0;
        assertNotEquals( unexpected, actual);
    }

    @Test
    void removeCourseByID() {
        CourseDAO courseDAO = new CourseDAO();
        boolean result = courseDAO.removeCourseByID(idCourse);
        assertTrue(result);
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
        CourseDAO courseDAO = new CourseDAO();
        List<Course> courses = courseDAO.getAllCourses();
        int result = courses.size();
        int expected = 1;
        assertEquals(expected, result);
    }
}