package test.org.database.dao;

import org.database.dao.ProjectDAO;
import org.domain.Project;
import org.junit.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
* ProjectDAO Tester. 
* 
* @author <Angel Adrian Camal Garcia>
* @since <pre>abr 14, 2020</pre> 
* @version 1.0 
*/ 
public class ProjectDAOTest { 

    @Test
    public void addProject() {

    }

    @Test
    public void removeProjectByID() {

    }

    @Test
    public void updateProjectInformation() {

    }

    @Test
    public void addSelectedProjectByPractitionerID() {

    }

    @Test
    public void getSelectedProjectsByPractitionerID() {

    }

    @Test
    public void getAssignedProjectByPractitionerID() {

    }

    @Test
    public void testGetAllAvailableProjectsFromLastCourse() throws Exception {
        ProjectDAO projectDAO = new ProjectDAO();
        List<Project> projectsAvailableFromLastCourse = projectDAO.getAllAvailableProjectsFromLastCourse();
        assertNotNull(projectsAvailableFromLastCourse);
    }

    @Test
    public void testGetAllProjectsFromLastCourse() throws Exception {
        ProjectDAO projectDAO = new ProjectDAO();
        List<Project> projectsFromLastCourse = projectDAO.getAllProjectsFromLastCourse();
        assertNotNull(projectsFromLastCourse);
    }

} 
