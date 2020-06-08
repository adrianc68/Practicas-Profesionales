package org.database.dao;

import org.database.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
     * Change a password to person
     * <p>
     * This method it's used when someone needs to change a password
     * to their account.
     * </p>
     * @param password the password to set
     * @param idUser the id user to change password
     * @return true if any row was affected otherwise it returns false
     */
    @Override
    public boolean changePasswordByIdUser(String password, int idUser) throws SQLException {
        int rowsAffected = 0;
        try( Connection conn = database.getConnection() ) {
            conn.setAutoCommit(false);
            String statement = "UPDATE AccessAccount SET password = ? WHERE id_user = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, idUser);
            rowsAffected = preparedStatement.executeUpdate();
            conn.commit();
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return rowsAffected > 0;
    }

    /***
     * Generate a code by email
     * <p>
     * This method it's used when someone has lost her password and need to recover it
     * It generates a new code in database
     * </p>
     * @param emailRecovery the email to generate the code
     * @return true if any row was affected and false if any row was not affected.
     */
    @Override
    public boolean generatePasswordRecoveryCodeByEmail(String emailRecovery) throws SQLException {
        int rowsAffected = 0;
        try( Connection conn = database.getConnection() ) {
            conn.setAutoCommit(false);
            String statement = "UPDATE AccessAccount SET password_recovery_code = SUBSTRING(MD5(RAND()),-8) WHERE email = ? ORDER BY id_user DESC LIMIT 1";
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setString(1, emailRecovery);
            rowsAffected = preparedStatement.executeUpdate();
            conn.commit();
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return rowsAffected > 0;
    }

    /***
     * Change password by email
     * <p>
     * This method it's used when someone has not logged in and needs to change his password
     * </p>
     * @param password the new password to replace de oldpassword
     * @param email the account's email to change it password.
     * @return true if any row was affected and false if any row was no affected.
     */
    @Override
    public boolean changePasswordByEmail(String password, String email) throws SQLException {
        int rowsAffected = 0;
        try( Connection conn = database.getConnection() ) {
            conn.setAutoCommit(false);
            String statement = "UPDATE AccessAccount SET password = ? WHERE email = ? ORDER BY id_user DESC LIMIT 1";
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, email);
            rowsAffected = preparedStatement.executeUpdate();
            conn.commit();
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return rowsAffected > 0;
    }

    /***
     * Get the recovery code by Email
     * <p>
     * This method is used to validate the code sent against the code entered by the user
     * </p>
     * @param emailRecovery the account's email to get the recovery code.
     * @return String representing the recovery code.
     */
    @Override
    public String getRecoveryCodeByEmail(String emailRecovery) throws SQLException {
        String code = null;
        try( Connection conn = database.getConnection() ) {
            conn.setAutoCommit(false);
            String statement = "SELECT password_recovery_code FROM AccessAccount WHERE email = ? ORDER BY id_user DESC LIMIT 1;";
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setString(1, emailRecovery);
            ResultSet resultSet = preparedStatement.executeQuery();
            if( resultSet.next() ) {
                code = resultSet.getString("password_recovery_code");
            }
            conn.commit();
        } catch (SQLException sqlException) {
            throw sqlException;
        }
        return code;
    }

}
