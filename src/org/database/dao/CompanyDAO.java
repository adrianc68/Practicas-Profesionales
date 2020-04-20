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
    /***
     * Constant for the connection to the database
     */
    private final Database database;
    /***
     * Query results
     */
    private ResultSet result;

    /***
     * CompanyDAO constructor.
     * This constructor initialize a connection to the database.
     */
    public CompanyDAO() {
        database = new Database();
    }

    /***
     * Add a company to database.
     * <p>
     * This method add a company to database and returns the row number affected
     * by this method. This method is used by the coordinator when he is adding
     * a project and the company of the project doesn't exist.
     * </p>
     * @param company the company that you want to add to database
     * @return the row number affected by this method
     */
    @Override
    public int addCompany(Company company) {
        int rowsAffected = 0;
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
            insertCompany.setString(7, company.getSector().getSector() );
            insertCompany.setString(8, company.getCity() );
            insertCompany.setInt(9, company.getCoordinator().getId() );
            insertCompany.setInt(10,company.getCourse().getId() );
            rowsAffected = insertCompany.executeUpdate();
            conn.commit();
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(CompanyDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return rowsAffected;
    }

    /***
     * Return a company ID.
     * <p>
     * This method will query the specified company.
     * </p>
     * @param name the company name
     * @param id_course the course ID of the company
     * @return return the ID company. return -1 if no company was find it.
     */
    @Override
    public int getIDCompany(String name, int id_course) {
        int idCompany = -1;
        try(Connection conn = database.getConnection() ){
            String statement = "SELECT id_company FROM Company WHERE name = ? AND id_course = ?";
            PreparedStatement queryIDCompany = conn.prepareStatement(statement);
            queryIDCompany.setString(1, name);
            queryIDCompany.setInt(2, id_course);
            result = queryIDCompany.executeQuery();
            result.next();
            idCompany = result.getInt(1);
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(CompanyDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return idCompany;
    }

    /***
     * Return all the companies from the actual/last course.
     * <p>
     * This method will get all the companies from the database.
     * This method is used by the coordinator when he is adding a project
     * and need see if the project's company exist.
     * </p>
     * @return List<Company>
     */
    @Override
    public List<Company> getAllCompaniesFromLastCourse()  {
        List<Company> companies = new ArrayList<>();
        Company company = null;
        Coordinator coordinator = null;
        Course course = null;
        try(Connection conn = database.getConnection() ){
            String statement = "SELECT C.id_company, C.name, C.address, C.email, C.state, C.direct_users, C.indirect_users, C.sector, C.city, C.phoneNumber, CT.cubicle, CT.staff_number, P.id_person, P.name, P.phoneNumber, P.email, CS.id_course, CS.NRC, CS.Period, CS.name, CS.id_course FROM Company AS C INNER JOIN Coordinator AS CT ON C.id_coordinator = CT.id_person INNER JOIN Person AS P ON C.id_coordinator = P.id_person INNER JOIN Course AS CS ON C.id_course = CS.id_course and CS.id_course = (SELECT max(id_course) FROM Course)";
            PreparedStatement queryCompanies = conn.prepareStatement(statement);
            result = queryCompanies.executeQuery();
            while( result.next() ){
                company = new Company();
                coordinator = new Coordinator();
                course = new Course();
                course.setId( result.getInt("CS.id_course") );
                course.setNRC( result.getString("CS.NRC") );
                course.setPeriod( result.getString("CS.period") );
                course.setName( result.getString("CS.name") );
                coordinator.setId( result.getInt("P.id_person") );
                coordinator.setName( result.getString("P.name") );
                coordinator.setPhoneNumber( result.getString("P.phoneNumber") );
                coordinator.setEmail( result.getString("P.email") );
                coordinator.setCourse(course);
                coordinator.setCubicle( result.getInt("CT.cubicle") );
                coordinator.setStaff_number( result.getString("CT.staff_number") );
                company.setId( result.getInt("id_company") );
                company.setName( result.getString("name") );
                company.setAddress( result.getString("address") );
                company.setEmail( result.getString("email") );
                company.setState( result.getString("state") );
                company.setDirectUsers( result.getInt("direct_users") );
                company.setIndirectUsers( result.getInt("indirect_users") );
                company.setSector(Sector.valueOf( result.getString("C.Sector").toUpperCase()) );
                company.setCity( result.getString("city") );
                company.setCoordinator(coordinator);
                company.setCourse(course);
                company.setPhoneNumber( result.getString("phoneNumber") );
                companies.add(company);
            }
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger(CompanyDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return companies;
    }

}
