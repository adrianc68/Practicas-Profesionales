package org.database.dao.projectdao;

import org.database.dao.ProjectDAO;
import org.domain.Project;
import org.junit.Test;
import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetProjectTest {

    @Test
    public void getSelectedProjectsByPractitionerID() throws SQLException {
        int idPractitioner = 8;
        List<Project> selectedProject = new ProjectDAO().getSelectedProjectsByPractitionerID(idPractitioner);
        int expected = 3;
        int actual = selectedProject.size();
        assertEquals(expected,actual);
    }

    @Test
    public void getAssignedProjectByPractitionerID() throws SQLException {
        int idPractitioner = 8;
        Project assignedProject = new ProjectDAO().getAssignedProjectByPractitionerID(idPractitioner);
        assertNotNull(assignedProject);
    }

    @Test
    public void getAllAvailableProjectsFromLastCourse() throws SQLException {
        List<Project> projectList = new ProjectDAO().getAllAvailableProjectsFromLastCourse();
        int expected = 1;
        int actual = projectList.size();
        assertEquals(expected,actual);
    }

    @Test
    public void getAllProjectsFromLastCourse() throws SQLException {
        List<Project> projectList = new ProjectDAO().getAllProjectsFromLastCourse();
        int expected = 1;
        int actual = projectList.size();
        assertEquals(expected,actual);
    }

}
