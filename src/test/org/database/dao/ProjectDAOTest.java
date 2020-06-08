package test.org.database.dao;

import org.database.dao.ProjectDAO;
import org.domain.Organization;
import org.domain.Course;
import org.domain.Project;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.sql.SQLException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
* ProjectDAO Tester. 
* 
* @author <Angel Adrian Camal Garcia>
* @since <pre>abr 14, 2020</pre> 
* @version 1.0 
*/
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProjectDAOTest {
    private final int idProject = 2;
    private final int idPractitioner = 3;

    @Test
    public void addProject() throws SQLException {
        Project project = new Project();
        project.setName("Implementacion de metodologia de desarollo de software");
        project.setDuration(420);
        project.setSchedule("Lunes a jueves 12:30-16:30");
        project.setGeneralDescription("Se hara un cambio de metodologia dentro de la corporacion");
        project.setGeneralPurpose("Cambiar la metodologia de desarrollo de software");
        project.setNameResponsable("Jorge Hernandez Hernandez");
        project.setChargeResponsable("CEO");
        project.setEmailResponsable("ceomet@microsoft.com");
        Organization organization = new Organization();
        organization.setId(1);
        Course course = new Course();
        course.setId(1);
        organization.setCourse(course);
        project.setOrganization(organization);
        project.setActivities(new ArrayList<>());
        project.setImmediateObjetives(new ArrayList<>());
        project.setMethodologies(new ArrayList<>());
        project.setMediateObjectives(new ArrayList<>());
        project.setResources(new ArrayList<>());
        project.setResponsibilities(new ArrayList<>());
        ProjectDAO projectDAO = new ProjectDAO();
        int expected = idProject;
        int actual = projectDAO.addProject(project);
        assertEquals(expected, actual);
    }

    @Test
    public void bUpdateProjectInformation() throws SQLException {
        Project project = new Project();
        project.setName("Implementación de metodologia de desarollo SCRUM");
        project.setDuration(420);
        project.setSchedule("Lunes a jueves 12:30-16:30");
        project.setGeneralDescription("Se hara un cambio de metodologia dentro de la corporación");
        project.setGeneralPurpose("Cambiar la metodologia de desarrollo de software");
        project.setNameResponsable("Francisco Hernandez Gonzalez");
        project.setChargeResponsable("CEO");
        project.setEmailResponsable("franCEO@microsoft.com");
        project.setId(idProject);
        Organization organization = new Organization();
        organization.setId(1);
        Course course = new Course();
        course.setId(1);
        organization.setCourse(course);
        project.setOrganization(organization);
        project.setActivities(new ArrayList<>());
        project.setImmediateObjetives(new ArrayList<>());
        project.setMethodologies(new ArrayList<>());
        project.setMediateObjectives(new ArrayList<>());
        project.setResources(new ArrayList<>());
        project.setResponsibilities(new ArrayList<>());
        ProjectDAO projectDAO = new ProjectDAO();
        assertTrue(projectDAO.updateProjectInformation(project));
    }

    @Test
    public void addSelectedProjectByPractitionerID() {
//        ProjectDAO projectDAO = new ProjectDAO();
//        assertTrue(projectDAO.addSelectedProjectByPractitionerID(idPractitioner, idProject));
    }

    @Test
    public void getSelectedProjectsByPractitionerID() throws SQLException {
        ProjectDAO projectDAO = new ProjectDAO();
        int expected = 0;
        int result = projectDAO.getSelectedProjectsByPractitionerID(idPractitioner).size();
        assertEquals(expected, result);
    }

    @Test
    public void getAssignedProjectByPractitionerID() throws SQLException {
        ProjectDAO projectDAO = new ProjectDAO();
        Project project = projectDAO.getAssignedProjectByPractitionerID(idPractitioner);
        int expected = 1;
        int actual = project.getId();
        assertEquals(expected, actual);
    }

    @Test
    public void gtestGetAllAvailableProjectsFromLastCourse() throws Exception {
        ProjectDAO projectDAO = new ProjectDAO();
        int expected = 1;
        int actual = projectDAO.getAllAvailableProjectsFromLastCourse().size();
        assertEquals(expected, actual);
    }

    @Test
    public void gtestGetAllProjectsFromLastCourse() throws Exception {
        ProjectDAO projectDAO = new ProjectDAO();
        int expected = 2;
        int actual = projectDAO.getAllProjectsFromLastCourse().size();
        assertEquals(expected, actual);
    }

    @Test
    public void removeProjectByID() throws SQLException {
        ProjectDAO projectDAO = new ProjectDAO();
        assertTrue(projectDAO.removeProjectByID(idProject));
    }

} 
