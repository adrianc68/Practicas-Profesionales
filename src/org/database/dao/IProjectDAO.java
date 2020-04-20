package org.database.dao;

import org.domain.Project;

import java.util.List;

public interface IProjectDAO {
    /***
     * Add a project to database.
     * <p>
     * This method receive a instance of project and then add it to database.
     * This method is used by coordinator when he needs to add a project
     * </p>
     * @param project the project to be added to database
     * @return the row number affected by this method
     */
    int addProject(Project project);

    /***
     * Remove a project from database.
     * <p>
     * This method will remove a project from database. It's used by Coordinator when he needs
     * to remove a project for any reason.
     * </p>
     * @param idProject the project's id to be removed from database.
     * @return return the rows number affected by this method.
     */
    int removeProjectByID(int idProject);

    /***
     * Update the project information from a existing project in database.
     * <p>
     * This method receive a project and replaces the information in database
     * </p>
     * @param project the project with updated information.
     * @return the rows number affected by this method
     */
    int updateProjectInformation(Project project);

    /***
     * Add in the datase a project selected by a Practitioner
     * <p>
     * This method add to a practitioner the projects selected by himself. This method is used by
     * the practitioner.
     * </p>
     * @param idPractitioner practicing's id to set the selected project
     * @param idProject project's id to set.
     * @return an int representing the rows number affected in database.
     */
    int addSelectedProjectByPractitioner(int idPractitioner, int idProject);


    /***
     * Get all unassigned projects or available of the actual/last course from the database.
     * <p>
     * This method get all available projects of the last course. This method is intended
     * for the coordinator and practitioners.
     * </p>
     * @return List<Project> a list containing the available projects
     */
    List<Project> getAllAvailableProjectsFromLastCourse();

    /***
     * Get the projects selected by a practitioner.
     * <p>
     * This method return a List, which contain the selected projects by a Practitioners. It's used by
     * the coordinator as a support to assign a project to a Practitioner.
     * </p>
     * @param idPractitioner the practitioner's ID
     * @return an List which contain the projects selected by a practitioner.
     */
    List<Project> getSelectedProjectsByIDPractitioner(int idPractitioner);

    /***
     * Get all projects of the actual/last course from the database.
     * <p>
     * This method get all projects of the last course from the database.
     * This method is used by the coordinator.
     * </p>
     * @return List<Project> a list containing all projects of the actual/last course
     */
    List<Project> getAllProjectsFromLastCourse();

}
