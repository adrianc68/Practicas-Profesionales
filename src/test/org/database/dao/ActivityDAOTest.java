package test.org.database.dao;

import org.database.DatabaseProperties;
import org.database.dao.ActivityDAO;
import org.domain.Activity;
import org.domain.Course;
import org.domain.Professor;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ActivityDAOTest {

    private final int idActivity = 1;
    private final int idProfessor = 1;

    @Test
    void addActivity() {
        ActivityDAO activityDAO = new ActivityDAO();
        Course course = new Course();
        course.setId(1);
        Professor professor = new Professor();
        professor.setCourse(course);
        professor.setId(1);
        Activity activity = new Activity();
        activity.setName("Reporte Mensual");
        activity.setDescription("Subir el documento correspondiente al reporte mensual");
        activity.setDeadline(null);
        activity.setProfessor(professor);
        int actual = activityDAO.addActivity(activity);
        assertNotNull(actual);
    }

    @Test
    void removeActivityByID() {
        ActivityDAO activityDAO = new ActivityDAO();
        assertTrue( activityDAO.removeActivityByID(idActivity) );
    }

    @Test
    void getAllActivitiesFromLastCourse() {
        ActivityDAO activityDAO = new ActivityDAO();
        int expected = 1;
        int actual = activityDAO.getAllActivitiesFromLastCourse().size();
        assertEquals(expected, actual);
    }

    @Test
    void getAllActivitiesByProfessor() {
        ActivityDAO activityDAO = new ActivityDAO();
        int expected = 1;
        int actual = activityDAO.getAllActivitiesByProfessorID(idProfessor).size();
        assertEquals(expected, actual);
    }
}