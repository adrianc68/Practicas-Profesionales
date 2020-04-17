package test.org.database.dao; 

import org.database.dao.CompanyDAO;
import org.domain.Company;
import org.junit.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/** 
* CompanyDAO Tester. 
* 
* @author <Angel Adrian Camal Garcia>
* @since <pre>abr 16, 2020</pre> 
* @version 1.0 proposed
*/ 
public class CompanyDAOTest { 

    @Test
    public void testGetIDCompany() throws Exception {
        CompanyDAO companyDAO = new CompanyDAO();
        int company = companyDAO.getIDCompany("NOT EXISTING COMPANY", 1);
        assertNull(company);
    }

    @Test
    public void testGetAllCompaniesFromLastCourse() throws Exception {
        CompanyDAO companyDAO = new CompanyDAO();
        List<Company> companiesFromLastCourse = companyDAO.getAllCompaniesFromLastCourse();
        assertNotNull(companiesFromLastCourse);
    }

} 
