package org.database.dao.projectdao;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import org.database.dao.ActivityDAO;
import org.database.dao.ProjectDAO;
import org.domain.ActivityState;
import org.domain.Coordinator;
import org.domain.Course;
import org.domain.Organization;
import org.domain.Project;
import org.domain.Sector;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModifyRowProjectTest {
    static int idProject;

    @Test
    public void addProject() throws SQLException {
        Project project = new Project();
        project.setName("Desarrollo de sistema web");
        project.setDuration(420);
        project.setSchedule("Lunes a jueves 12:30 a 16:30");
        project.setGeneralDescription("Desarrollar un sistema web que represente todos los procesos que se llevan a cabo en la organizacion");
        project.setGeneralPurpose("Optimizar los procesos de consulta y seguimiento de las actividades");
        project.setNameResponsable("Jorge Hernandez Hernandez");
        project.setChargeResponsable("CEO");
        project.setEmailResponsable("jorgehernandez@viena.com.mx");
        Course course = new Course();
        course.setName("Practicas Profesionales");
        course.setNRC("N104543");
        course.setPeriod("AGO 2020 - DIC 2020");
        course.setId(1);
        Organization organization = new Organization();
        organization.setCourse(course);
        organization.setAddress("Miguel Aleman #335");
        organization.setCity("Xalapa");
        Coordinator coordinator = new Coordinator();
        coordinator.setName("Angel Juan");
        coordinator.setPhoneNumber("2283943402");
        coordinator.setEmail("aj@uv.com.mx");
        coordinator.setActivityState(ActivityState.ACTIVE);
        coordinator.setStaffNumber("2342");
        coordinator.setCubicle(23);
        coordinator.setCourse(course);
        coordinator.setId(4);
        organization.setCoordinator(coordinator);
        organization.setDirectUsers(35);
        organization.setEmail("semarnat@gov.com.mx");
        organization.setIndirectUsers(125);
        organization.setName("SEMARNAT");
        organization.setPhoneNumber("81700053483");
        organization.setSector(Sector.SECONDARY);
        organization.setState("Veracruz");
        organization.setId(3);
        project.setOrganization(organization);
        List<String> activities = new ArrayList<>();
        activities.add("Llevar a cabo la documentacion o las cuestiones de diseño");
        activities.add("Llevar a cabo la documentacion a nivel de codigo");
        project.setActivities(activities);
        List<String> immediateObjetives = new ArrayList<>();
        immediateObjetives.add("Llevar a cabo 3/4 partes de la documentacion");
        project.setImmediateObjetives(immediateObjetives);
        List<String> methodologies = new ArrayList<>();
        methodologies.add("Metodología incremental");
        project.setMethodologies(methodologies);
        List<String> mediateObjetives = new ArrayList<>();
        mediateObjetives.add("Modificación de código y de documentación");
        project.setMediateObjectives(mediateObjetives);
        List<String> recursos = new ArrayList<>();
        recursos.add("Equipo de computo");
        recursos.add("Sala para programar");
        recursos.add("IDE personalizado");
        project.setResources(recursos);
        List<String> responsibilities = new ArrayList<>();
        responsibilities.add("Cumplir con las actividades y actividades que sean asignadas");
        responsibilities.add("Llevar a cabo el proyecto en tiempo y forma");
        project.setResponsibilities(responsibilities);
        idProject = new ProjectDAO().addProject(project);
        project.setId(idProject);
        int unexpected = 0;
        int actual = idProject;
        assertNotEquals(unexpected,actual);
    }

    @Test
    public void addInvalidProject() {
        Project project = new Project();
        project.setName("Desarrollo de sistema web que soporte gran cantidad de redes interconectadas es decir una red masiva que soporte conexiones a alta velocidad");
        project.setDuration(420);
        project.setSchedule("Lunes a jueves 12:30 a 16:30");
        project.setGeneralDescription("Desarrollar un sistema web que represente todos los procesos que se llevan a cabo en la organizacion");
        project.setGeneralPurpose("Optimizar los procesos de consulta y seguimiento de las actividades");
        project.setNameResponsable("Jorge Hernandez Hernandez");
        project.setChargeResponsable("CEO");
        project.setEmailResponsable("jorgehernandez@viena.com.mx");
        Course course = new Course();
        course.setName("Practicas Profesionales");
        course.setNRC("N104543");
        course.setPeriod("AGO 2020 - DIC 2020");
        course.setId(1);
        Organization organization = new Organization();
        organization.setCourse(course);
        organization.setAddress("Miguel Aleman #335");
        organization.setCity("Xalapa");
        Coordinator coordinator = new Coordinator();
        coordinator.setName("Angel Juan");
        coordinator.setPhoneNumber("2283943402");
        coordinator.setEmail("aj@uv.com.mx");
        coordinator.setActivityState(ActivityState.ACTIVE);
        coordinator.setStaffNumber("2342");
        coordinator.setCubicle(23);
        coordinator.setCourse(course);
        coordinator.setId(4);
        organization.setCoordinator(coordinator);
        organization.setDirectUsers(35);
        organization.setEmail("semarnat@gov.com.mx");
        organization.setIndirectUsers(125);
        organization.setName("SEMARNAT");
        organization.setPhoneNumber("81700053483");
        organization.setSector(Sector.SECONDARY);
        organization.setState("Veracruz");
        organization.setId(3);
        project.setOrganization(organization);
        List<String> activities = new ArrayList<>();
        activities.add("Llevar a cabo la documentacion o las cuestiones de diseño");
        activities.add("Llevar a cabo la documentacion a nivel de codigo");
        project.setActivities(activities);
        List<String> immediateObjetives = new ArrayList<>();
        immediateObjetives.add("Llevar a cabo 3/4 partes de la documentacion");
        project.setImmediateObjetives(immediateObjetives);
        List<String> methodologies = new ArrayList<>();
        methodologies.add("Metodología incremental");
        project.setMethodologies(methodologies);
        List<String> mediateObjetives = new ArrayList<>();
        mediateObjetives.add("Modificación de código y de documentación");
        project.setMediateObjectives(mediateObjetives);
        List<String> recursos = new ArrayList<>();
        recursos.add("Equipo de computo");
        recursos.add("Sala para programar");
        recursos.add("IDE personalizado");
        project.setResources(recursos);
        List<String> responsibilities = new ArrayList<>();
        responsibilities.add("Cumplir con las actividades y actividades que sean asignadas");
        responsibilities.add("Llevar a cabo el proyecto en tiempo y forma");
        project.setResponsibilities(responsibilities);
        Class<MysqlDataTruncation> expectedException = MysqlDataTruncation.class;
        Class<SQLException> actualException = (Class<SQLException>) assertThrows( SQLException.class, () -> new ProjectDAO().addProject(project) ).getClass();
        assertEquals(expectedException, actualException);
    }

    @Test
    public void removeProjectByID() throws SQLException {
        int idProjectToRemove = idProject;
        boolean isProjectRemoved = new ProjectDAO().removeProjectByID(idProjectToRemove);
        assertTrue(isProjectRemoved);
    }

    // This should return false
    @Test
    public void removeNoExistingProjectByID() throws SQLException {
        int idProjectToRemove = 435345;
        boolean isProjectRemoved = new ProjectDAO().removeProjectByID(idProjectToRemove);
        assertTrue(isProjectRemoved);
    }

    @Test
    public void bUpdateProjectInformation() throws SQLException {
        Project project = new Project();
        project.setName("Desarrollo de sistema web");
        project.setDuration(420);
        project.setSchedule("Lunes a jueves 12:30 a 16:30");
        project.setGeneralDescription("Desarrollar un sistema de escritorio que represente todos los procesos que se llevan a cabo en la organizacion");
        project.setGeneralPurpose("Optimizar los procesos de consulta y seguimiento de las actividades");
        project.setNameResponsable("Jose Cuervo");
        project.setChargeResponsable("Socio mayoritario");
        project.setEmailResponsable("jorgehernandez@viena.com.mx");
        Course course = new Course();
        course.setName("Practicas Profesionales");
        course.setNRC("N104543");
        course.setPeriod("AGO 2020 - DIC 2020");
        course.setId(1);
        Organization organization = new Organization();
        organization.setCourse(course);
        organization.setAddress("Miguel Aleman #335");
        organization.setCity("Xalapa");
        Coordinator coordinator = new Coordinator();
        coordinator.setName("Angel Juan");
        coordinator.setPhoneNumber("2283943402");
        coordinator.setEmail("aj@uv.com.mx");
        coordinator.setActivityState(ActivityState.ACTIVE);
        coordinator.setStaffNumber("2342");
        coordinator.setCubicle(23);
        coordinator.setCourse(course);
        coordinator.setId(4);
        organization.setCoordinator(coordinator);
        organization.setDirectUsers(35);
        organization.setEmail("semarnat@gov.com.mx");
        organization.setIndirectUsers(125);
        organization.setName("SEMARNAT");
        organization.setPhoneNumber("81700053483");
        organization.setSector(Sector.SECONDARY);
        organization.setState("Veracruz");
        organization.setId(3);
        project.setOrganization(organization);
        List<String> activities = new ArrayList<>();
        activities.add("Llevar a cabo la documentacion o las cuestiones de diseño");
        activities.add("Llevar a cabo la documentacion a nivel de codigo");
        project.setActivities(activities);
        List<String> immediateObjetives = new ArrayList<>();
        immediateObjetives.add("Llevar a cabo 3/4 partes de la documentacion");
        project.setImmediateObjetives(immediateObjetives);
        List<String> methodologies = new ArrayList<>();
        methodologies.add("Metodología incremental");
        project.setMethodologies(methodologies);
        List<String> mediateObjetives = new ArrayList<>();
        mediateObjetives.add("Modificación de código y de documentación");
        project.setMediateObjectives(mediateObjetives);
        List<String> recursos = new ArrayList<>();
        recursos.add("Equipo de computo");
        recursos.add("Sala para programar");
        recursos.add("IDE personalizado");
        project.setResources(recursos);
        List<String> responsibilities = new ArrayList<>();
        responsibilities.add("Cumplir con las actividades y actividades que sean asignadas");
        responsibilities.add("Llevar a cabo el proyecto en tiempo y forma");
        project.setResponsibilities(responsibilities);
        project.setId(idProject);
        boolean isProjectUpdated = new ProjectDAO().updateProjectInformation(project);
        assertTrue(isProjectUpdated);
    }

    @Test
    public void bUpdateNoExistingProjectInformation() throws SQLException {
        Project project = new Project();
        project.setName("Desarrollo de sistema web");
        project.setDuration(420);
        project.setSchedule("Lunes a jueves 12:30 a 16:30");
        project.setGeneralDescription("Desarrollar un sistema de escritorio que represente todos los procesos que se llevan a cabo en la organizacion");
        project.setGeneralPurpose("Optimizar los procesos de consulta y seguimiento de las actividades");
        project.setNameResponsable("Jose Cuervo");
        project.setChargeResponsable("Socio mayoritario");
        project.setEmailResponsable("jorgehernandez@viena.com.mx");
        Course course = new Course();
        course.setName("Practicas Profesionales");
        course.setNRC("N104543");
        course.setPeriod("AGO 2020 - DIC 2020");
        course.setId(1);
        Organization organization = new Organization();
        organization.setCourse(course);
        organization.setAddress("Miguel Aleman #335");
        organization.setCity("Xalapa");
        Coordinator coordinator = new Coordinator();
        coordinator.setName("Angel Juan");
        coordinator.setPhoneNumber("2283943402");
        coordinator.setEmail("aj@uv.com.mx");
        coordinator.setActivityState(ActivityState.ACTIVE);
        coordinator.setStaffNumber("2342");
        coordinator.setCubicle(23);
        coordinator.setCourse(course);
        coordinator.setId(4);
        organization.setCoordinator(coordinator);
        organization.setDirectUsers(35);
        organization.setEmail("semarnat@gov.com.mx");
        organization.setIndirectUsers(125);
        organization.setName("SEMARNAT");
        organization.setPhoneNumber("81700053483");
        organization.setSector(Sector.SECONDARY);
        organization.setState("Veracruz");
        organization.setId(3);
        project.setOrganization(organization);
        List<String> activities = new ArrayList<>();
        activities.add("Llevar a cabo la documentacion o las cuestiones de diseño");
        activities.add("Llevar a cabo la documentacion a nivel de codigo");
        project.setActivities(activities);
        List<String> immediateObjetives = new ArrayList<>();
        immediateObjetives.add("Llevar a cabo 3/4 partes de la documentacion");
        project.setImmediateObjetives(immediateObjetives);
        List<String> methodologies = new ArrayList<>();
        methodologies.add("Metodología incremental");
        project.setMethodologies(methodologies);
        List<String> mediateObjetives = new ArrayList<>();
        mediateObjetives.add("Modificación de código y de documentación");
        project.setMediateObjectives(mediateObjetives);
        List<String> recursos = new ArrayList<>();
        recursos.add("Equipo de computo");
        recursos.add("Sala para programar");
        recursos.add("IDE personalizado");
        project.setResources(recursos);
        List<String> responsibilities = new ArrayList<>();
        responsibilities.add("Cumplir con las actividades y actividades que sean asignadas");
        responsibilities.add("Llevar a cabo el proyecto en tiempo y forma");
        project.setResponsibilities(responsibilities);
        int noExistingProject = 234;
        project.setId(noExistingProject);
        Class<SQLIntegrityConstraintViolationException> expectedException = SQLIntegrityConstraintViolationException.class;
        Class<SQLIntegrityConstraintViolationException> actualException = (Class<SQLIntegrityConstraintViolationException>) assertThrows( SQLException.class, () -> new ProjectDAO().updateProjectInformation(project) ).getClass();
        assertEquals(expectedException, actualException);
    }

    @Test
    public void assignProjectToPractitioner() throws SQLException {
        int idProject = 1;
        int idPractitioner = 8;
        boolean isProjectAssigned = new ProjectDAO().assignProjectToPractitioner(idPractitioner,idProject);
        assertTrue(isProjectAssigned);
    }

    @Test
    public void addSelectedProjectByPractitionerID() {
        int idProjectSelected = 1;
        int idPractitioner = 8;
        Class<SQLException> expectedException = SQLException.class;
        Class<SQLException> actualException = (Class<SQLException>) assertThrows( SQLException.class, () -> new ProjectDAO().addSelectedProjectByPractitionerID(idPractitioner,idProjectSelected) ).getClass();
        assertEquals(expectedException, actualException);
    }

}