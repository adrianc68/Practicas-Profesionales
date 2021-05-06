package org.database.dao.activitydao;

import org.database.dao.ActivityDAO;
import org.domain.Activity;
import org.junit.Test;
import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetAllActivitiesTest {
    @Test
    public void getAllActivitiesFromLastCourse() throws SQLException {
        List<Activity> activityList = new ActivityDAO().getAllActivitiesFromLastCourse();
        int expected = 3;
        int actual = activityList.size();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllActivitiesByProfessorID() throws SQLException {
        int idProfessor = 5;
        List<Activity> activityList = new ActivityDAO().getAllActivitiesByProfessorID(idProfessor);
        int expected = 3;
        int actual = activityList.size();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllActivitiesByNoExistingProfessorID() throws SQLException {
        int idProfessor = 493489534;
        List<Activity> activityList = new ActivityDAO().getAllActivitiesByProfessorID(idProfessor);
        int expected = 0;
        int actual = activityList.size();
        assertEquals(expected, actual);
    }

}