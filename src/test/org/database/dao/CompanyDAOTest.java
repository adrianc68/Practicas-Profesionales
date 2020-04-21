package test.org.database.dao; 

import org.database.dao.CompanyDAO;
import org.domain.Company;
import org.domain.Coordinator;
import org.domain.Course;
import org.domain.Sector;
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

    @Test
    public void testAddCompany() {
        Company actualCompany = new Company();
        actualCompany.setName("TEST METHOD COMPANY");
        actualCompany.setAddress("TEST METHOD COMPANY");
        actualCompany.setEmail("TEST_METHOD@hotmail.com");
        actualCompany.setState("TEST METHOD");
        actualCompany.setPhoneNumber("TEST METHOD");
        actualCompany.setDirectUsers(0);
        actualCompany.setIndirectUsers(0);
        actualCompany.setSector(Sector.PRIMARY);
        actualCompany.setCity("TEST METHOD");
        Coordinator coordinator = new Coordinator();
        coordinator.setName("TEST METHOD");
        coordinator.setId(1);
        Course course = new Course();
        course.setId(2);
        actualCompany.setCourse(course);
        actualCompany.setCoordinator(coordinator);
        CompanyDAO companyDAO = new CompanyDAO();
        boolean actual = companyDAO.addCompany(actualCompany);
        assertTrue(actual);
    }

    @Test
    public void testGetAllCompaniesFromLastCourse() {
        CompanyDAO companyDAO = new CompanyDAO();
        List<Company> companiesFromLastCourse = companyDAO.getAllCompaniesFromLastCourse();
        assertNotNull(companiesFromLastCourse);
    }

} 
