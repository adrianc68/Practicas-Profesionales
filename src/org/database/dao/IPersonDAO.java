package org.database.dao;

import org.domain.ActivityState;
import org.domain.Person;

import java.sql.SQLException;

public interface IPersonDAO {
    /***
     * Return a person from database.
     * <p>
     * This method its used by Auth class and return a object person
     * that can be cast to coordinator or professor or practitioner.
     * </p>
     * @param email the email to be compared with data from database
     * @param password the password to be compared with data from database
     * @return Person an object Person.
     */
    Person getPersonByEmailAndPassword(String email, String password);

    /***
     * Change activity state to person.
     * <p>
     * This method changes the activity state to person. It is used to
     * avoid the access to person.
     * </p>
     * @param idPractitioner
     * @return true if any row was affected, and false if it was any row not affected.
     */
    boolean changeActivityStateByID(int idPractitioner);

    /***
     * Get the activity state by email and password.
     * <p>
     * This method it's used by system when it needs to check if person has
     * an activity state as active to allow the user access to system.
     * </p>
     * @param idPerson the user to get their activity state
     * @return ActivityState representing the activity state.
     */
    ActivityState getActivityStateByID(int idPerson);

}
