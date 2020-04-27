package test.org.database.dao; 

import org.database.DatabaseProperties;
import org.database.dao.CompanyDAO;
import org.domain.Company;
import org.domain.Coordinator;
import org.domain.Course;
import org.domain.Sector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/** 
* CompanyDAO Tester. 
* 
* @author <Angel Adrian Camal Garcia>
* @since <pre>abr 16, 2020</pre> 
* @version 1.0 proposed
*/

public class CompanyDAOTest {
    private DatabaseProperties databaseProperties;

    public CompanyDAOTest() {
        databaseProperties = new DatabaseProperties();
        databaseProperties.setPath("database.properties");
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
    public void testAddCompany() {
        Company company = new Company();
        company.setName("Microsoft");
        company.setAddress("Republica de Uruguay 13 Centro de Mexico");
        company.setEmail("microsoft@corp.com");
        company.setState("Ciudad de Mexico");
        company.setPhoneNumber("2281035659");
        company.setDirectUsers(230);
        company.setIndirectUsers(550);
        company.setSector(Sector.TERTIARY);
        company.setCity("Ciudad de Mexico");
        Coordinator coordinator = new Coordinator();
        coordinator.setId(1);
        Course course = new Course();
        course.setId(1);
        company.setCourse(course);
        company.setCoordinator(coordinator);
        CompanyDAO companyDAO = new CompanyDAO();
        int actual = companyDAO.addCompany(company);
        assertNotNull(actual);
    }

    @Test
    public void testAddCompanyWithLongAttributes() {
        Company company = new Company();
        company.setName("Microsoft con union a Huawei Technologies como Compañia Ilimitada y Pixar Inc");
        company.setAddress("Republica de Uruguay 13 Centro de Mexico");
        company.setEmail("microsoft@corp.com");
        company.setState("Ciudad de Mexico");
        company.setPhoneNumber("2281035659");
        company.setDirectUsers(230);
        company.setIndirectUsers(550);
        company.setSector(Sector.TERTIARY);
        company.setCity("Ciudad de Mexico");
        Coordinator coordinator = new Coordinator();
        coordinator.setId(1);
        Course course = new Course();
        course.setId(1);
        company.setCourse(course);
        company.setCoordinator(coordinator);
        CompanyDAO companyDAO = new CompanyDAO();
        int actual = companyDAO.addCompany(company);
        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    public void testGetAllCompaniesFromLastCourse() {
        CompanyDAO companyDAO = new CompanyDAO();
        List<Company> companiesFromLastCourse = companyDAO.getAllCompaniesFromLastCourse();
        assertNotNull(companiesFromLastCourse);
    }

} 
