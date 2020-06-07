package org.database.dao;

import org.domain.Project;
import java.util.List;

public interface IProjectDAO {
    /***
     * Add a project to database.
     * <p>
     * This method is used by coordinator when he needs to add a project
     * </p>
     * @param project the project to be added to database
     * @return int representing the project's ID
     */
    int addProject(Project project);

    /***
     * Remove a project from database.
     * <p>
     * It's used by Coordinator when he needs to remove a project for any reason.
     * </p>
     * @param idProject the project's id to be removed from database.
     * @return boolean true if 1 o more than 1 rows are affected
     */
    boolean removeProjectByID(int idProject);

    /***
     * Update the project information from a existing project in database.
     * <p>
     * This method receive a project and replaces the information in database
     * </p>
     * @param project the project with updated information.
     * @return boolean true if project was removed.
     */
    boolean updateProjectInformation(Project project);

    /***
     * Assign a project to a Practitioner in database.
     * <p>
     * It's used by the coordinator when he decided which project assign to a practitioner
     * </p>
     * @param idPractitioner the practitioner's ID
     * @param idProject the project's ID to be assigned to practitioner
     * @return boolean true if 1 o more than 1 rows are affected
     */
    boolean assignProjectToPractitioner(int idPractitioner, int idProject);

    /***
     * Add in the datase a project selected by a Practitioner
     * <p>
     * This method is used by the practitioner to add a project selected by himself.
     * </p>
     * @param idPractitioner practitioner's id to set the selected project
     * @param idProject project's id to set.
     * @return boolean true if 1 o more than 1 rows are affected
     */
    boolean addSelectedProjectByPractitionerID(int idPractitioner, int idProject);

    /***
     * Get the projects selected by a practitioner.
     * <p>
     * It's used by the coordinator as a support to assign a project to a Practitioner.
     * </p>
     * @param idPractitioner the practitioner's ID
     * @return an List which contain the projects selected by a practitioner.
     */
    List<Project> getSelectedProjectsByPractitionerID(int idPractitioner);

    /***
     * Get the assigned project of a practitioner.
     * <p>
     * It's used by the coordinator to view which project is assigned to a practitioner
     * </p>
     * @param idPractitioner the practitioner's ID
     * @return a Project of a Practitioner.
     */
    Project getAssignedProjectByPractitionerID(int idPractitioner);

    /***
     * Get all unassigned projects or available of the actual/last course from the database.
     * <p>
     * This method is intended for the coordinator and practitioners.
     * </p>
     * @return List<Project> a list containing the available projects
     */
    List<Project> getAllAvailableProjectsFromLastCourse();

    /***
     * Get all projects of the actual/last course from the database.
     * <p>.
     * This method is used by the coordinator to verify the projects added.
     * </p>
     * @return List<Project> a list containing all projects of the actual/last course
     */
    List<Project> getAllProjectsFromLastCourse();

}
