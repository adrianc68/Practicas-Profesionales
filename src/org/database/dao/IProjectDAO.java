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
     * Get all unassigned projects or available of the actual/last course from the database.
     * <p>
     * This method get all available projects of the last course. This method is intended
     * for the coordinator and practitioners.
     * </p>
     * @return List<Project> a list containing the available projects
     */
    List<Project> getAllAvailableProjectsFromLastCourse();

    /***
     * Get the project list according to the condition received as parameter
     * <p>
     * This method get the project list according to the parameter.
     * The goal is re-use code.
     * </p>
     * @param statement
     * @return
     */
    List<Project> getAllProjectsFromLastCourse();

}
