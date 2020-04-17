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
