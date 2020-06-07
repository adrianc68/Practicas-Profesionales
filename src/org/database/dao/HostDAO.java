package org.database.dao;

import org.database.Database;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HostDAO implements IHostDAO {
    private final Database database;

    /***
     * HostDAO constructor.
     * This constructor initialize a connection to the database.
     */
    public HostDAO() {
        this.database = new Database();
    }

    /***
     * Get attempts done by mac address.
     * <p>
     * This method get the number of attempts done by a specified mac address
     * or user.
     * </p>
     * @param address the host that is using the system.
     * @return int representing the attemps done by host.
     */
    @Override
    public int getAttemptsByMacAddress(String address) {
        int attempts = -1;
        try(Connection conn = database.getConnection() ){
            conn.setAutoCommit(false);
            String statement = "SELECT attempts FROM Host WHERE mac_address = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setString(1, address);
            ResultSet resultSet = preparedStatement.executeQuery();
            if( resultSet.next() ) {
                attempts = resultSet.getInt("attempts");
            }
            conn.commit();
        } catch (SQLException sqlException) {
            Logger.getLogger( HostDAO.class.getName() ).log(Level.SEVERE, null, sqlException);
        }
        return attempts;
    }

    /***
     * Send the host's address
     * <p>
     * This method send the host's address when he is trying to log in
     * to the system. Furthermore, tracks the number of attempts.
     * </p>
     * @param address the host's address
     * @return boolean if any row was affected on database and false if any row was no affected.
     */
    @Override
    public boolean sendActualMacAddress(String address) {
        boolean executed = false;
        try(Connection conn = database.getConnection() ){
            conn.setAutoCommit(false);
            String statement = "CALL sendAddress(?)";
            CallableStatement callableStatement = conn.prepareCall(statement);
            callableStatement.setString(1, address);
            executed = callableStatement.execute();
            conn.commit();
        } catch (SQLException sqlException) {
            Logger.getLogger( HostDAO.class.getName() ).log(Level.SEVERE, null, sqlException);
        }
        return executed;
    }

    /***
     * Reset the attempt's done by host.
     * <p>
     * This method reset the attempt's done by host. The objective is reset the attempt's done
     * when the user was able to log in to the system.
     * </p>
     * @param address the host's address
     * @return true if any row was affected on database and false if any row was not affected.
     */
    @Override
    public boolean resetAttempts(String address) {
        boolean executed = false;
        try(Connection conn = database.getConnection() ){
            conn.setAutoCommit(false);
            String statement = "UPDATE Host SET attempts = 0 WHERE mac_address = ?";
            CallableStatement callableStatement = conn.prepareCall(statement);
            callableStatement.setString(1, address);
            executed = callableStatement.execute();
            conn.commit();
        } catch (SQLException sqlException) {
            Logger.getLogger( HostDAO.class.getName() ).log(Level.SEVERE, null, sqlException);
        }
        return executed;
    }

}
