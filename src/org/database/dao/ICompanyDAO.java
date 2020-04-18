package org.database.dao;

import org.domain.Company;

import java.util.List;

public interface ICompanyDAO {
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
    int addCompany(Company company);

    /***
     * Return a company ID.
     * <p>
     * This method will query the specified company.
     * </p>
     * @param name the company name
     * @param id_course the course ID of the company
     * @return return the ID company. return -1 if no company was find it.
     */
    int getIDCompany(String name, int id_course);

    /***
     * Return all the companies from the actual/last course.
     * <p>
     * This method will get all the companies from the database.
     * This method is used by the coordinator when he is adding a project
     * and need see if the project's company exist.
     * </p>
     * @return List<Company>
     */
    List<Company> getAllCompaniesFromLastCourse();

}
