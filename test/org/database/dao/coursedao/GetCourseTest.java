package org.database.dao.coursedao;

import org.database.dao.CourseDAO;
import org.domain.Course;
import org.junit.Test;
import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetCourseTest {
    @Test
    public void getLastCourse() throws SQLException {
        Course course = new CourseDAO().getLastCourse();
        assertNotNull(course);
    }

    @Test
    public void getAllCourses() throws SQLException {
        List<Course> courseList = new CourseDAO().getAllCourses();
        int expected = 1;
        int actual = courseList.size();
        assertEquals(expected,actual);
    }

}
