package org.database.dao;

import org.domain.Professor;
import java.util.List;

public interface IProfessorDAO {

    /***
     * Add a new professor to Course.
     * <p>
     * This method is used by Administrator when he needs someone to
     * manage the activities of a course.
     * </p>
     * @param professor the professor to be added to database.
     * @return int representing the professor's id.
     */
    int addProfessor(Professor professor);

    /***
     * Remove a professor from course.
     * <p>
     * This method is used by Administrator when he needs to remove a professor
     * for any reason.
     * </p>
     * @param idProfessor professor's id to be removed.
     * @return boolean true if professor was deleted from database.
     */
    boolean removeProfessorByID(int  idProfessor);

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
     * Get all professors from last/actual course
     * <p>
     * This method it's used by Coordinator when he is assigning a professor to a practitioner.
     * </p>
     * @return List<Professor> a list with all professors from last course.
     */
    List<Professor> getAllProfessorsFromLastCourse();

    /***
     * Get all professors by a specified course
     * <p>
     * This method it's used by Administrator when he needs to look up
     * all professors from a specified course
     * </p>
     * @return List<Professor> a list with all professors from specified course
     */
    List<Professor> getAllProfessorsByCourseID(int idCourse);



}
