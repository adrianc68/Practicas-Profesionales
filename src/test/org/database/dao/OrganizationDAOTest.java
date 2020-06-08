package test.org.database.dao; 

import org.database.DatabaseProperties;
import org.database.dao.OrganizationDAO;
import org.domain.Organization;
import org.domain.Coordinator;
import org.domain.Course;
import org.domain.Sector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/** 
* CompanyDAO Tester. 
* 
* @author <Angel Adrian Camal Garcia>
* @since <pre>abr 16, 2020</pre> 
* @version 1.0 proposed
*/

public class OrganizationDAOTest {
    private DatabaseProperties databaseProperties;

    public OrganizationDAOTest() {
        databaseProperties = new DatabaseProperties("database.properties");
    }

    @Before
    public void before() {
        databaseProperties.writeProperties("anonimo", "12345", "jdbc:mysql://localhost:3306/PracticesTest?useTimezone=true&serverTimezone=UTC");
    }

    @After
    public void after(){
        databaseProperties.writeProperties("anonimo", "12345", "jdbc:mysql://localhost:3306/Practices?useTimezone=true&serverTimezone=UTC");
    }

    @Test
    public void testAddCompany() throws SQLException {
        Organization organization = new Organization();
        organization.setName("Microsoft");
        organization.setAddress("Republica de Uruguay 13 Centro de Mexico");
        organization.setEmail("microsoft@corp.com");
        organization.setState("Ciudad de Mexico");
        organization.setPhoneNumber("2281035659");
        organization.setDirectUsers(230);
        organization.setIndirectUsers(550);
        organization.setSector(Sector.TERTIARY);
        organization.setCity("Ciudad de Mexico");
        Coordinator coordinator = new Coordinator();
        coordinator.setId(1);
        Course course = new Course();
        course.setId(1);
        organization.setCourse(course);
        organization.setCoordinator(coordinator);
        OrganizationDAO organizationDAO = new OrganizationDAO();
        int actual = organizationDAO.addOrganization(organization);
        assertNotNull(actual);
    }

    @Test
    public void testAddCompanyWithLongAttributes() throws SQLException {
        Organization organization = new Organization();
        organization.setName("Microsoft con union a Huawei Technologies como Compañia Ilimitada y Pixar Inc");
        organization.setAddress("Republica de Uruguay 13 Centro de Mexico");
        organization.setEmail("microsoft@corp.com");
        organization.setState("Ciudad de Mexico");
        organization.setPhoneNumber("2281035659");
        organization.setDirectUsers(230);
        organization.setIndirectUsers(550);
        organization.setSector(Sector.TERTIARY);
        organization.setCity("Ciudad de Mexico");
        Coordinator coordinator = new Coordinator();
        coordinator.setId(1);
        Course course = new Course();
        course.setId(1);
        organization.setCourse(course);
        organization.setCoordinator(coordinator);
        OrganizationDAO organizationDAO = new OrganizationDAO();
        int actual = organizationDAO.addOrganization(organization);
        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    public void testGetAllCompaniesFromLastCourse() throws SQLException {
        OrganizationDAO organizationDAO = new OrganizationDAO();
        List<Organization> companiesFromLastCourse = organizationDAO.getAllCompaniesFromLastCourse();
        assertNotNull(companiesFromLastCourse);
    }

} 
