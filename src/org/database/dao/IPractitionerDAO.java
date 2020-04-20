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
     * Delete a assign of professor to a Practitioner in the database.
     * <p>
     * This method remove a assign of professor to a student. It's used by the coordinator when
     * he decided to remove a assign of professor to a practitioner.
     * </p>
     * @param idPractitioner the practitioner's ID
     * @return an int representing the rows number affected in database.
     */
    int removeAssignProfessor(int idPractitioner);

    /***
     * Get all practitioners from the actual/last course
     * <p>
     * This method get the practitioners and their assigned projects.
     * This method is used by the coordinator and professor.
     * Doesn't return any selected project and any activity delivery
     * </p>
     * @return List<Practitioner> a list with practitioners of the actual/last course from database
     */
    List<Practitioner> getAllPractitionersFromLastCourse();

}
