package org.database.dao;

import java.sql.SQLException;

public interface IHostDAO {
    /***
     * Get attempts done by mac address.
     * <p>
     * This method get the number of attempts done by a specified mac address
     * or user.
     * </p>
     * @param address the host that is using the system.
     * @return int representing the attemps done by host.
     */
    int getAttemptsByMacAddress(String address) throws SQLException;

    /***
     * Send the host's address
     * <p>
     * This method send the host's address when he is trying to log in
     * to the system. Furthermore, tracks the number of attempts.
     * </p>
     * @param address the host's address
     * @return boolean if any row was affected on database and false if any row was no affected.
     */
    boolean sendActualMacAddress(String address) throws SQLException;

    /***
     * Reset the attempt's done by host.
     * <p>
     * This method reset the attempt's done by host. The objective is reset the attempt's done
     * when the user was able to log in to the system.
     * </p>
     * @param address the host's address
     * @return true if any row was affected on database and false if any row was not affected.
     */
    boolean resetAttempts(String address) throws SQLException;
}
