package org.database.dao.coursedao;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import org.database.dao.CourseDAO;
import org.domain.Course;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModifyRowCourseTest {
    static int idCourse;

    @Test
    public void addCourse() throws SQLException {
        Course course = new Course();
        course.setName("Practicas Profesionales");
        course.setNRC("N10453");
        course.setPeriod("AGO 2100 - DIC 2100");
        idCourse = new CourseDAO().addCourse(course);
        course.setId(idCourse);
        int unexpected = 0;
        int actual = course.getId();
        assertNotEquals(unexpected,actual);
    }

    @Test
    public void addInvalidCourse() {
        Course course = new Course();
        course.setName("Practicas ProfesionalesPracticas ProfesionalesPracticas ProfesionalesPracticas ProfesionalesPracticas ProfesionalesPracticas ProfesionalesPracticas ProfesionalesPracticas ProfesionalesPracticas Profesionales ");
        course.setNRC("N10453");
        course.setPeriod("AGO 2100 - DIC 2100");
        Class<MysqlDataTruncation> expectedException = MysqlDataTruncation.class;
        Class<SQLException> actualException = (Class<SQLException>) assertThrows( SQLException.class, () ->  new CourseDAO().addCourse(course) ).getClass();
        assertEquals(expectedException, actualException);
    }

    @Test
    public void removeCourseByID() throws SQLException {
        int idActualCourse = idCourse;
        boolean isCourseRemoved = new CourseDAO().removeCourseByID(idActualCourse);
        assertTrue(isCourseRemoved);
    }

}