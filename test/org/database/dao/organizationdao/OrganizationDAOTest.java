package org.database.dao.organizationdao;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import org.database.dao.OrganizationDAO;
import org.domain.ActivityState;
import org.domain.Coordinator;
import org.domain.Course;
import org.domain.Organization;
import org.domain.Sector;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrganizationDAOTest {

    @Test
    void addOrganization() throws SQLException {
        Organization newOrganization = new Organization();
        Course course = new Course();
        course.setName("Practicas Profesionales");
        course.setNRC("N104543");
        course.setPeriod("AGO 2020 - DIC 2020");
        course.setId(1);
        newOrganization.setCourse(course);
        newOrganization.setAddress("Miguel Aleman #335");
        newOrganization.setCity("Xalapa");
        Coordinator coordinator = new Coordinator();
        coordinator.setName("Angel Juan");
        coordinator.setPhoneNumber("2283943402");
        coordinator.setEmail("aj@uv.com.mx");
        coordinator.setActivityState(ActivityState.ACTIVE);
        coordinator.setStaffNumber("2342");
        coordinator.setCubicle(23);
        coordinator.setCourse(course);
        coordinator.setId(4);
        newOrganization.setCoordinator(coordinator);
        newOrganization.setDirectUsers(35);
        newOrganization.setEmail("semarnat@gov.com.mx");
        newOrganization.setIndirectUsers(125);
        newOrganization.setName("SEMARNAT");
        newOrganization.setPhoneNumber("81700053483");
        newOrganization.setSector(Sector.SECONDARY);
        newOrganization.setState("Veracruz");
        int idOrganization = new OrganizationDAO().addOrganization(newOrganization);
        newOrganization.setId(idOrganization);
        int unexpected = 0;
        int actual = idOrganization;
        assertNotEquals(unexpected,actual);
    }

    @Test
    void addInvalidOrganization() {
        Organization newOrganization = new Organization();
        Course course = new Course();
        course.setName("Practicas Profesionales");
        course.setNRC("N104543");
        course.setPeriod("AGO 2020 - DIC 2020");
        course.setId(1);
        newOrganization.setCourse(course);
        newOrganization.setAddress("Miguel Aleman #335");
        newOrganization.setCity("Xalapa");
        Coordinator coordinator = new Coordinator();
        coordinator.setName("Angel Juan");
        coordinator.setPhoneNumber("2283943402");
        coordinator.setEmail("aj@uv.com.mx");
        coordinator.setActivityState(ActivityState.ACTIVE);
        coordinator.setStaffNumber("2342");
        coordinator.setCubicle(23);
        coordinator.setCourse(course);
        coordinator.setId(4);
        newOrganization.setCoordinator(coordinator);
        newOrganization.setDirectUsers(35);
        newOrganization.setEmail("semarnat@gov.com.mx");
        newOrganization.setIndirectUsers(125);
        newOrganization.setName("Univesidad de Cambridge Cambridge Cambridge Cambridge Cambridge Cambridge Cambridge Cambridge Cambridge Cambridge");
        newOrganization.setPhoneNumber("81700053483");
        newOrganization.setSector(Sector.SECONDARY);
        newOrganization.setState("Veracruz");
        Class<MysqlDataTruncation> expectedException = MysqlDataTruncation.class;
        Class<SQLException> actualException = (Class<SQLException>) assertThrows( SQLException.class, () ->  new OrganizationDAO().addOrganization(newOrganization) ).getClass();
        assertEquals(expectedException, actualException);
    }

    @Test
    void getAllCompaniesFromLastCourse() throws SQLException {
        List<Organization> organizationList = new OrganizationDAO().getAllCompaniesFromLastCourse();
        assertNotNull(organizationList);
    }

}