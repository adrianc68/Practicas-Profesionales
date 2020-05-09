package org.database.dao;

import org.database.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccessAccountDAO implements IAccessAccountDAO {
    private final Database database;

    /***
     * AccountAccess constructor.
     * This constructor initialize a connection to the database.
     */
    public AccessAccountDAO() {
        database = new Database();
    }

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
    @Override
    public boolean addPasswordToUser(String password, int idUser) {
        int rowsAffected = 0;
        try( Connection conn = database.getConnection() ) {
            conn.setAutoCommit(false);
            String statement = "UPDATE AccessAccount SET password = ? WHERE id_user = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, idUser);
            rowsAffected = preparedStatement.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            Logger.getLogger( AccessAccountDAO.class.getName() ).log(Level.SEVERE, null, e);
        }
        return rowsAffected > 0;
    }
}
