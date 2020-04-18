package org.database.dao;

import org.domain.Practitioner;
import org.domain.Project;
import java.util.List;

public interface IPractitionerDAO {
    /***
     * Add a Practitioner to database.
     * <p>
     * This method add a practitioner to database and return the rows number affected
     * by this method. This method is used by the coordinator when he need to add a practicing.
     * </p>
     * @param practitioner to be added to database
     * @return an int representing the rows number affected in database.
     */
    int addPractitioner(Practitioner practitioner);

    /***
     * Delete a Practitioner from database.
     * <p>
     * This method remove a practitioner by an id provided. It's used by the coordinator when
     * he needs to remove a practicing for any reason.
     * </p>
     * @param idPractitioner the practitioner's ID
     * @return an int representing the rows number affected in database.
     */
    int removePractitioner(int idPractitioner);

    /***
     * Assign a project to a Practitioner in database.
     * <p>
     * This method update the assigned project of a practitioner. It's used by the coordinator when
     * he decided which project assign to a practitioner
     * </p>
     * @param idPractitioner the practitioner's ID
     * @param idProject the project's ID to be assigned to practitioner
     * @return an int representing the rows number affected in database.
     */
    int assignProject(int idPractitioner, int idProject);

    /***
     * Assign a professor to a Practitioner in the database.
     * <p>
     * This method assign a professor to a student. It's used by the coordinator when he decided assign
     * a professor to a practitioner.
     * </p>
     * @param idPractitioner the practitioner's ID
     * @param idProfessor the professor's id to be assigned to practitioner
     * @return an int representing the rows number affected in database.
     */
    int assignProfessor(int idPractitioner, int idProfessor);

    /***
     * Add in the datase a project selected by a Practitioner
     * <p>
     * This method add to a practitioner the projects selected by himself. This method is used by
     * the practitioner.
     * </p>
     * @param practitioner
     * @return an int representing the rows number affected in database.
     */
    int addSelectedProjectByPractitioner(Practitioner practitioner);

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

}
