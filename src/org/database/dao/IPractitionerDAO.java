package org.database.dao;

import org.domain.Practitioner;

import java.sql.SQLException;
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
    int addPractitioner(Practitioner practitioner) throws SQLException;

    /***
     * Delete a Practitioner from database.
     * <p>
     * It's used by the coordinator whem he needs to remove a practicing for any reason.
     * </p>
     * @param idPractitioner the practitioner's ID
     * @return boolean true if 1 o more than 1 rows are affected
     */
    boolean removePractitioner(int idPractitioner) throws SQLException;

    /***
     * Get a Practitioner by a specific delivery.
     * <p>
     * This method it's used by Professor when he needs to look a delivery.
     * </p>
     * @param idDelivery to get the practitioner
     * @return Practitioner
     * @throws SQLException
     */
    Practitioner getPractitionerByDelivery(int idDelivery) throws SQLException;

    /***
     * Get all practitioners and their assigned projects from the actual/last course
     * <p>
     * This method is used by the coordinator and professor.
     * Doesn't return with any activity delivery, selected project, assigned project or assigned professor
     * </p>
     * @return List<Practitioner> a list with practitioners of the actual/last course from database
     */
    List<Practitioner> getAllPractitionersFromLastCourse() throws SQLException;

    /***
     * Get all practitioners by a Professor
     * <p>
     * This method is used by the the professor when he needs to look
     * who is assigned to him.
     * </p>
     * @return List<Practitioner> a list with practitioners assigned to professor.
     */
    List<Practitioner> getAllPractitionersByProfessor(int idProfessor) throws SQLException;

}
