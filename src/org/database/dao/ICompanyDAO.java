package org.database.dao;

import org.domain.Company;
import java.util.List;

public interface ICompanyDAO {
    /***
     * Add a company to database.
     * <p>
     * This method is used by the coordinator when he is adding
     * a project and the company's project doesn't exist.
     * </p>
     * @param company the company that you want to add to database
     * @return int representing the company's id
     */
    int addCompany(Company company);

    /***
     * Return all the companies from the actual/last course.
     * <p>
     * This method is used by the coordinator when he is adding a project
     * and needs to see if the project's company exist.
     * </p>
     * @return List<Company>
     */
    List<Company> getAllCompaniesFromLastCourse();

}
