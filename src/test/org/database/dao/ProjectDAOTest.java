package test.org.database.dao;

import org.database.dao.ProjectDAO;
import org.domain.Company;
import org.domain.Course;
import org.domain.Project;
import org.domain.Sector;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

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
        Project project = new Project();
        project.setName("PROJECT TEST");
        project.setDuration(0);
        project.setSchedule("TEST");
        project.setGeneralDescription("TEST");
        project.setGeneralPurpose("TEST");
        project.setNameResponsable("TEST");
        project.setChargeResponsable("TEST");
        project.setEmailResponsable("TEST@HOTMAIL.COM");
        Company company = new Company();
        company.setId(1);
        Course course = new Course();
        course.setId(1);
        company.setCourse(course);
        project.setCompany(company);
        project.setActivities(new ArrayList<>());
        project.setImmediateObjetives(new ArrayList<>());
        project.setMethodologies(new ArrayList<>());
        project.setMediateObjectives(new ArrayList<>());
        project.setResources(new ArrayList<>());
        project.setResponsibilities(new ArrayList<>());
        ProjectDAO projectDAO = new ProjectDAO();
        assertTrue(projectDAO.addProject(project));
    }

    @Test
    public void removeProjectByID() {
        ProjectDAO projectDAO = new ProjectDAO();
        assertTrue(projectDAO.removeProjectByID(8));
    }

    @Test
    public void updateProjectInformation() {
        Project project = new Project();
        project.setName("PROJECT TEST UPDATE");
        project.setDuration(0);
        project.setSchedule("TEST");
        project.setGeneralDescription("TEST");
        project.setGeneralPurpose("TEST");
        project.setNameResponsable("TEST");
        project.setChargeResponsable("TEST");
        project.setEmailResponsable("TEST@HOTMAIL.COM");
        Company company = new Company();
        company.setId(1);
        Course course = new Course();
        course.setId(1);
        company.setCourse(course);
        project.setCompany(company);
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
        ProjectDAO projectDAO = new ProjectDAO();
        projectDAO.addSelectedProjectByPractitionerID(3, 5);
    }

    @Test
    public void getSelectedProjectsByPractitionerID() {
        ProjectDAO projectDAO = new ProjectDAO();
        int expected = 3;
        int result = projectDAO.getSelectedProjectsByPractitionerID(3).size();
        assertEquals(expected, result);
    }

    @Test
    public void getAssignedProjectByPractitionerID() {
        ProjectDAO projectDAO = new ProjectDAO();
        Project project = projectDAO.getAssignedProjectByPractitionerID(9);
        assertNotNull(project);
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
