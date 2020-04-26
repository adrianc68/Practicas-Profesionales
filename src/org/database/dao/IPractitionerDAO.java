package org.database.dao;

import org.domain.Practitioner;
import java.util.List;

public interface IPractitionerDAO {
    /***
     * Add a Practitioner to database.
     * <p>
     * This method is used by the coordinator when he needs to add a practicing.
     * </p>
     * @param practitioner to be added to database
     * @return int the practitioner's id
     */
    int addPractitioner(Practitioner practitioner);

    /***
     * Delete a Practitioner from database.
     * <p>
     * It's used by the coordinator whem he needs to remove a practicing for any reason.
     * </p>
     * @param idPractitioner the practitioner's ID
     * @return boolean true if 1 o more than 1 rows are affected
     */
    boolean removePractitioner(int idPractitioner);

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
     * Assign a professor to a Practitioner in the database.
     * <p>
     * It's used by the coordinator when he decided assign a professor to a practitioner.
     * </p>
     * @param idPractitioner the practitioner's ID
     * @param idProfessor the professor's id to be assigned to practitioner
     * @return boolean true if 1 o more than 1 rows are affected
     */
    boolean assignProfessorToPractitioner(int idPractitioner, int idProfessor);

    /***
     * Delete a assign of professor to a Practitioner in the database.
     * <p>
     * It's used by the coordinator when he decided to remove a assign of professor to a practitioner.
     * </p>
     * @param idPractitioner the practitioner's ID
     * @return boolean true if 1 o more than 1 rows are affected
     */
    boolean removeAssignedProfessorToPractitioner(int idPractitioner);

    /***
     * Get all practitioners and their assigned projects from the actual/last course
     * <p>
     * This method is used by the coordinator and professor.
     * Doesn't return with any activity delivery, selected project, assigned project or assigned professor
     * </p>
     * @return List<Practitioner> a list with practitioners of the actual/last course from database
     */
    List<Practitioner> getAllPractitionersFromLastCourse();

}
