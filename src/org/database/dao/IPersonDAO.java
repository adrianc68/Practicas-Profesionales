package org.database.dao;

import org.domain.Person;

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

}
