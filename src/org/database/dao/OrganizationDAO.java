package org.database.dao;

import org.domain.Organization;
import org.domain.Coordinator;
import org.domain.Course;
import org.domain.Sector;
import org.database.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrganizationDAO implements IOrganizationDAO {
    private final Database database;

    public OrganizationDAO() {
        database = new Database();
    }

    /***
     * Add a organization to database.
     * <p>
     * This method is used by the coordinator when he is adding
     * a project and the organization's project doesn't exist.
     * </p>
     * @param organization the organization that you want to add to database
     * @return int representing the organization's id
     */
    @Override
    public int addOrganization(Organization organization) throws SQLException {
        int idCompany = 0;
        try(Connection conn = database.getConnection() ){
            conn.setAutoCommit(false);
            String statement = "INSERT INTO Company(name, address, email, state, direct_users, indirect_users, sector, city, id_coordinator, id_course) values (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setString(1, organization.getName() );
            preparedStatement.setString(2, organization.getAddress() );
            preparedStatement.setString(3, organization.getEmail() );
            preparedStatement.setString(4, organization.getState() );
            preparedStatement.setInt(5, organization.getDirectUsers() );
            preparedStatement.setInt(6, organization.getIndirectUsers() );
            preparedStatement.setString(7, organization.getSector().name() );
            preparedStatement.setString(8, organization.getCity() );
            preparedStatement.setInt(9, organization.getCoordinator().getId() );
            preparedStatement.setInt(10, organization.getCourse().getId() );
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.executeQuery("SELECT LAST_INSERT_ID()");
            resultSet.next();
            idCompany = resultSet.getInt(1);
            conn.commit();
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return idCompany;
    }

    /***
     * Return all organizations from the actual/last course.
     * <p>
     * This method is used by the coordinator when he is adding a project
     * and needs to see if the project's organization exist.
     * </p>
     * @return List<Company>
     */
    @Override
    public List<Organization> getAllCompaniesFromLastCourse() throws SQLException {
        List<Organization> organizations = new ArrayList<>();
        try(Connection conn = database.getConnection() ){
            String statement = "SELECT COMP.id_company, COMP.name, COMP.address, COMP.email, COMP.state, COMP.direct_users, COMP.indirect_users, COMP.sector, COMP.city, COMP.phoneNumber,  COMP.id_course, CORD.cubicle, CORD.staff_number, PERSCORD.id_person, PERSCORD.name, PERSCORD.phoneNumber, PERSCORD.email, COUR.NRC, COUR.Period, COUR.name, COUR.id_course FROM Company AS COMP INNER JOIN Coordinator AS CORD ON COMP.id_coordinator = CORD.id_person INNER JOIN Person AS PERSCORD ON COMP.id_coordinator = PERSCORD.id_person INNER JOIN Course AS COUR ON COMP.id_course = COMP.id_course and COUR.id_course = (SELECT max(id_course) FROM Course)";
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while( resultSet.next() ){
                Course course = new Course();
                course.setId( resultSet.getInt("COUR.id_course") );
                course.setNRC( resultSet.getString("COUR.NRC") );
                course.setPeriod( resultSet.getString("COUR.period") );
                course.setName( resultSet.getString("COUR.name") );
                Coordinator coordinator = new Coordinator();
                coordinator.setId( resultSet.getInt("PERSCORD.id_person") );
                coordinator.setName( resultSet.getString("PERSCORD.name") );
                coordinator.setPhoneNumber( resultSet.getString("PERSCORD.phoneNumber") );
                coordinator.setEmail( resultSet.getString("PERSCORD.email") );
                coordinator.setCourse(course);
                coordinator.setCubicle( resultSet.getInt("CORD.cubicle") );
                coordinator.setStaffNumber( resultSet.getString("CORD.staff_number") );
                Organization organization = new Organization();
                organization.setId( resultSet.getInt("COMP.id_company") );
                organization.setName( resultSet.getString("COMP.name") );
                organization.setAddress( resultSet.getString("COMP.address") );
                organization.setEmail( resultSet.getString("COMP.email") );
                organization.setState( resultSet.getString("COMP.state") );
                organization.setDirectUsers( resultSet.getInt("COMP.direct_users") );
                organization.setIndirectUsers( resultSet.getInt("COMP.indirect_users") );
                organization.setSector(Sector.valueOf( resultSet.getString("COMP.Sector").toUpperCase() ) );
                organization.setCity( resultSet.getString("COMP.city") );
                organization.setCoordinator(coordinator);
                organization.setCourse(course);
                organization.setPhoneNumber( resultSet.getString("COMP.phoneNumber") );
                organizations.add(organization);
            }
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return organizations;
    }

}
