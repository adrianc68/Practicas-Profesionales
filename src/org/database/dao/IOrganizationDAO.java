package org.database.dao;

import org.domain.Organization;
import java.util.List;

public interface IOrganizationDAO {
    /***
     * Add a organization to database.
     * <p>
     * This method is used by the coordinator when he is adding
     * a project and the organization's project doesn't exist.
     * </p>
     * @param organization the organization that you want to add to database
     * @return int representing the organization's id
     */
    int addOrganization(Organization organization);

    /***
     * Return all organizations from the actual/last course.
     * <p>
     * This method is used by the coordinator when he is adding a project
     * and needs to see if the project's organization exist.
     * </p>
     * @return List<Company>
     */
    List<Organization> getAllCompaniesFromLastCourse();

}
