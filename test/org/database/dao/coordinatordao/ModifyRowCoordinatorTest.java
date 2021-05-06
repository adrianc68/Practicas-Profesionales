package org.database.dao.coordinatordao;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import org.database.dao.CoordinatorDAO;
import org.domain.ActivityState;
import org.domain.Coordinator;
import org.domain.Course;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.SQLException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModifyRowCoordinatorTest {
    static int lastCoordinatorID;

    @Test
    public void addCoordinator() throws SQLException {
        Coordinator newCoordinator = new Coordinator();
        newCoordinator.setCubicle(1);
        newCoordinator.setStaffNumber("318H2DA");
        newCoordinator.setActivityState(ActivityState.ACTIVE);
        Course course = new Course();
        course.setName("Practicas Profesionales");
        course.setNRC("N104543");
        course.setPeriod("AGO 2020 - DIC 2020");
        course.setId(1);
        newCoordinator.setCourse(course);
        newCoordinator.setName("Angel Juan Sanchez");
        newCoordinator.setPhoneNumber("2281394583");
        newCoordinator.setEmail("angelsj@uv.com.mx");
        lastCoordinatorID = new CoordinatorDAO().addCoordinator(newCoordinator);
        newCoordinator.setId(lastCoordinatorID);
        int unexpected = 0;
        int actual = newCoordinator.getId();
        assertNotEquals(unexpected,actual);
    }

    @Test
    public void addInvalidCoordinator() {
        Coordinator newCoordinator = new Coordinator();
        newCoordinator.setCubicle(1);
        newCoordinator.setStaffNumber("318H2DB");
        newCoordinator.setActivityState(ActivityState.ACTIVE);
        Course course = new Course();
        course.setName("Practicas Profesionales");
        course.setNRC("N104543");
        course.setPeriod("AGO 2020 - DIC 2020");
        course.setId(1);
        newCoordinator.setCourse(course);
        newCoordinator.setName("Gilberto Juan Sanchez Gilberto Juan Sanchez Gilberto Juan Sanchez Gilberto Juan Sanchez Gilberto Juan Sanchez Gilberto Juan Sanchez");
        newCoordinator.setPhoneNumber("22815494383");
        newCoordinator.setEmail("gilsj@uv.com.mx");
        newCoordinator.setId(lastCoordinatorID);
        Class<MysqlDataTruncation> expectedException = MysqlDataTruncation.class;
        Class<SQLException> actualException = (Class<SQLException>) assertThrows( SQLException.class, () -> new CoordinatorDAO().addCoordinator(newCoordinator) ).getClass();
        assertEquals(expectedException, actualException);
    }

    @Test
    public void removeCoordinator() throws SQLException {
        int idCoordinator = lastCoordinatorID;
        boolean isCoordinatorRemoved = new CoordinatorDAO().removeCoordinatorByID(idCoordinator);
        assertTrue(isCoordinatorRemoved);
    }

}
