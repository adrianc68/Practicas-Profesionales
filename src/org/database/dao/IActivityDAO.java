package org.database.dao;

import org.domain.Activity;
import org.domain.Professor;

import java.util.List;

public interface IActivityDAO {
    /***
     * Add an activity to database.
     * <p>
     * This method is used by the Practitioner when he/she needs to add an activity.
     * </p>
     * @param activity to be added to database.
     * @return int representing the activity's id.
     */
    int addActivity(Activity activity);
    /***
     * Remove an activity from database.
     * <p>
     * This method is used by Professor when he needs to remove an activity.
     * </p>
     * @param idActivity to be removed to database
     * @return boolean true if 1 o more than 1 rows are affected
     */
    boolean removeActivityByID(int idActivity);
    /***
     * Get all activities from the actual/last course
     * <p>
     * This method is used by the Professor when he needs to look up all the activities
     * </p>
     * @return List<activities> a list with activities of the actual/last course from database
     */
    List<Activity> getAllActivitiesFromLastCourse();
    /***
     * Get all activities looking for Professor.
     * <p>
     * This method is used by the Practitioner and Professor.
     * </p>
     * @param idProfessor the professor's id
     * @return List<activities> a list with activities looking for Professor.
     */
    List<Activity> getAllActivitiesByProfessorID(int idProfessor);

}
