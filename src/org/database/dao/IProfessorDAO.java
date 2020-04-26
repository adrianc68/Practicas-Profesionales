package org.database.dao;

import org.domain.Professor;
import java.util.List;

public interface IProfessorDAO {
    boolean addProfessor(Professor professor);
    boolean removeProfessor(Professor professor);

    /***
     * Get assigned professor of a practicing from datatabase.
     * <p>
     * This method it's used as a support to take an action on a practitioner
     * by the coordinator.
     * </p>
     * @param idPractitioner practitioner's id to get his assigned professor.
     * @return Professor professor assigned to a specific practitioner.
     */
    Professor getAssignedProfessorByPractitionerID(int idPractitioner);

    /***
     * Get all professors from actual/last course.
     * <p>
     * This method it's used by the coordinator when he is adding a new professor or
     * assigning a professor to a practitioner.
     * </p>
     * @return List<Professor> a list with all professors from last course.
     */
    List<Professor> getAllProfessorsFromLastCourse();

}
