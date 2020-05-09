package org.database.dao;

public interface IAccessAccountDAO {
    /***
     * Add a password to person
     * <p>
     * This method it's used by Coordinator and administrator to add an
     * practitioner, coordinator or professor. It allows the access to
     * this system.
     * </p>
     * @param password
     * @param idUser
     * @return
     */
    boolean addPasswordToUser(String password, int idUser);

}
