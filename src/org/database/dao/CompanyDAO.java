package org.database.dao;

import org.domain.Company;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompanyDAO implements ICompanyDAO{
    private final Database database;
    private ResultSet result;

    public CompanyDAO() {
        database = new Database();
    }

    /***
     * Add a company to database.
     * <p>
     * This method is used by the coordinator when he is adding
     * a project and the company's project doesn't exist.
     * </p>
     * @param company the company that you want to add to database
     * @return int representing the company's id
     */
    @Override
    public int addCompany(Company company) {
        int idCompany = 0;
        try(Connection conn = database.getConnection() ){
            conn.setAutoCommit(false);
            String statement = "INSERT INTO Company(name, address, email, state, direct_users, indirect_users, sector, city, id_coordinator, id_course) values (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement insertCompany = conn.prepareStatement(statement);
            insertCompany.setString(1, company.getName() );
            insertCompany.setString(2, company.getAddress() );
            insertCompany.setString(3, company.getEmail() );
            insertCompany.setString(4, company.getState() );
            insertCompany.setInt(5, company.getDirectUsers() );
            insertCompany.setInt(6, company.getIndirectUsers() );
            insertCompany.setString(7, company.getSector().name() );
            insertCompany.setString(8, company.getCity() );
            insertCompany.setInt(9, company.getCoordinator().getId() );
            insertCompany.setInt(10,company.getCourse().getId() );
            insertCompany.executeUpdate();
            result = insertCompany.executeQuery("SELECT LAST_INSERT_ID()");
            result.next();
            idCompany = result.getInt(1);
            conn.commit();
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(CompanyDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return idCompany;
    }

    /***
     * Return all the companies from the actual/last course.
     * <p>
     * This method is used by the coordinator when he is adding a project
     * and needs to see if the project's company exist.
     * </p>
     * @return List<Company>
     */
    @Override
    public List<Company> getAllCompaniesFromLastCourse()  {
        List<Company> companies = new ArrayList<>();
        try(Connection conn = database.getConnection() ){
            String statement = "SELECT COMP.id_company, COMP.name, COMP.address, COMP.email, COMP.state, COMP.direct_users, COMP.indirect_users, COMP.sector, COMP.city, COMP.phoneNumber,  COMP.id_course, CORD.cubicle, CORD.staff_number, PERSCORD.id_person, PERSCORD.name, PERSCORD.phoneNumber, PERSCORD.email, COUR.NRC, COUR.Period, COUR.name, COUR.id_course FROM Company AS COMP INNER JOIN Coordinator AS CORD ON COMP.id_coordinator = CORD.id_person INNER JOIN Person AS PERSCORD ON COMP.id_coordinator = PERSCORD.id_person INNER JOIN Course AS COUR ON COMP.id_course = COMP.id_course and COUR.id_course = (SELECT max(id_course) FROM Course)";
            PreparedStatement queryCompanies = conn.prepareStatement(statement);
            result = queryCompanies.executeQuery();
            while( result.next() ){
                Course course = new Course();
                course.setId( result.getInt("COUR.id_course") );
                course.setNRC( result.getString("COUR.NRC") );
                course.setPeriod( result.getString("COUR.period") );
                course.setName( result.getString("COUR.name") );
                Coordinator coordinator = new Coordinator();
                coordinator.setId( result.getInt("PERSCORD.id_person") );
                coordinator.setName( result.getString("PERSCORD.name") );
                coordinator.setPhoneNumber( result.getString("PERSCORD.phoneNumber") );
                coordinator.setEmail( result.getString("PERSCORD.email") );
                coordinator.setCourse(course);
                coordinator.setCubicle( result.getInt("CORD.cubicle") );
                coordinator.setStaffNumber( result.getString("CORD.staff_number") );
                Company company = new Company();
                company.setId( result.getInt("COMP.id_company") );
                company.setName( result.getString("COMP.name") );
                company.setAddress( result.getString("COMP.address") );
                company.setEmail( result.getString("COMP.email") );
                company.setState( result.getString("COMP.state") );
                company.setDirectUsers( result.getInt("COMP.direct_users") );
                company.setIndirectUsers( result.getInt("COMP.indirect_users") );
                company.setSector(Sector.valueOf( result.getString("COMP.Sector").toUpperCase()) );
                company.setCity( result.getString("COMP.city") );
                company.setCoordinator(coordinator);
                company.setCourse(course);
                company.setPhoneNumber( result.getString("COMP.phoneNumber") );
                companies.add(company);
            }
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(CompanyDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return companies;
    }

}
