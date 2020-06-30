package org.database.dao.activitydao;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import org.database.dao.ActivityDAO;
import org.domain.Activity;
import org.domain.ActivityState;
import org.domain.Course;
import org.domain.Professor;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.util.DateFormatter;
import java.sql.SQLException;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModifyRowActivityTest {
    static int idActivity;

    @Test
    public void addActivity() throws SQLException {
        Activity activity = new Activity();
        Professor professor = new Professor();
        professor.setCubicle(2);
        professor.setStaffNumber("233");
        professor.setActivityState(ActivityState.ACTIVE);
        Course course = new Course();
        course.setName("Practicas Profesionales");
        course.setNRC("N104543");
        course.setPeriod("AGO 2020 - DIC 2020");
        course.setId(1);
        professor.setCourse(course);
        professor.setEmail("p@hotmail.com");
        professor.setName("Juan Carlos");
        professor.setPhoneNumber("2232459685");
        professor.setId(5);
        activity.setProfessor(professor);
        activity.setDescription("Realizar las actividades de la página 350 y 348");
        activity.setName("RL06 Lectura de comprensión del capitulo VIII de Wiegers");
        activity.setDeliveries(null);
        activity.setDeadline("2020-06-26 12:30:00");
        idActivity = new ActivityDAO().addActivity(activity);
        activity.setId(idActivity);
        int unexpected = 0;
        int actual = activity.getId();
        assertNotEquals(unexpected, actual);
    }

    @Test
    public void addInvalidActivity() {
        Activity activity = new Activity();
        Professor professor = new Professor();
        professor.setCubicle(2);
        professor.setStaffNumber("233");
        professor.setActivityState(ActivityState.ACTIVE);
        Course course = new Course();
        course.setName("Practicas Profesionales");
        course.setNRC("N104543");
        course.setPeriod("AGO 2020 - DIC 2020");
        course.setId(1);
        professor.setCourse(course);
        professor.setEmail("p@hotmail.com");
        professor.setName("Juan Carlos");
        professor.setPhoneNumber("2232459685");
        professor.setId(5);
        activity.setProfessor(professor);
        activity.setDescription("Realizar las actividades de la página 350 y 348");
        activity.setName("RL06 Lectura de comprensión del capitulo VIII de Wiegers RL06 Lectura de comprensión del capitulo VIII de Wiegers RL06 Lectura de comprensión del capitulo VIII de Wiegers RL06 Lectura de comprensión del capitulo VIII de Wiegers RL06 Lectura de comprensión del capitulo VIII de Wiegers ");
        activity.setDeliveries(null);
        activity.setDeadline("2020-06-26 12:30:00");
        Class<MysqlDataTruncation> expectedException = MysqlDataTruncation.class;
        Class<SQLException> actualException = (Class<SQLException>) assertThrows( SQLException.class, () -> new ActivityDAO().addActivity(activity) ).getClass();
        assertEquals(expectedException, actualException);
    }

    @Test
    public void removeActivityByID() throws SQLException {
        boolean isRemoved = new ActivityDAO().removeActivityByID(idActivity);
        assertTrue(isRemoved);
    }

}
