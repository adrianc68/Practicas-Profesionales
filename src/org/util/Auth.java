package org.util;

import org.database.dao.AccessAccountDAO;
import org.database.dao.HostDAO;
import org.database.dao.PersonDAO;
import org.domain.ActivityState;
import org.domain.Person;
import org.util.exceptions.InactiveUserException;
import org.util.exceptions.LimitReachedException;
import org.util.exceptions.UserNotFoundException;
import java.sql.SQLException;

public class Auth {
    private static Auth instance;
    private Person currentUser;

    public static Auth getInstance() {
        if(instance == null) {
            instance = new Auth();
        }
        return instance;
    }

    public Person getCurrentUser() {
        return currentUser;
    }

    /***
     * Logout from actual current logged user.
     */
    public void logOut() {
        currentUser = null;
    }

    /***
     * Log in an account.
     * <p>
     * This method it is used to accesss to the system.
     * </p>
     * @param email the user's email
     * @param password
     * @return boolean true if login was successful and false if login was not successful
     * @throws UserNotFoundException
     * @throws LimitReachedException
     * @throws InactiveUserException
     * @throws SQLException
     */
    public boolean logIn(String email, String password) throws UserNotFoundException, LimitReachedException, InactiveUserException, SQLException {
        boolean isLogged;
        checkAttemptsLimit();
        sendMacAddress();
        Person user = getCurrentUser(email, password);
        resetAttempts();
        checkActivityState(user);
        isLogged = true;
        currentUser = user;
        return isLogged;
    }

    /**+
     * Generate a recovery code.
     * <p>
     * This method generate a random string in database.
     * The purpose is that user can change his password.
     * </p>
     * @param email representing the user's email
     * @return boolean if recovery code was generated and false if was not generated.
     * @throws SQLException
     */
    public boolean generateRecoveryCode(String email) throws SQLException {
        boolean isCodeGenerated = new AccessAccountDAO().generatePasswordRecoveryCodeByEmail( email );
        return isCodeGenerated;
    }

    /***
     * Return the recovery code from database.
     * <p>
     * This method returns the recovery code generated in database.
     * The purpose is that user can change his password
     * </p>
     * @param email representing the user's email
     * @return String representing the recovery code generated in database.
     * @throws SQLException
     */
    public String getRecoveryCode(String email) throws SQLException {
        String recoveryCode = new AccessAccountDAO().getRecoveryCodeByEmail(email);
        return recoveryCode;
    }

    /***
     * Change password from the current logged user.
     * <p>
     * This method it's used by the logged user when he needs to change his password by
     * any reason.
     * </p>
     * @param newPassword to be set it in database.
     * @return boolean true if password was reset and false if was not reset.
     * @throws SQLException
     */
    public boolean resetPassword(String newPassword) throws SQLException {
        int currentUserId = Auth.getInstance().getCurrentUser().getId();
        boolean isPasswordChanged = new AccessAccountDAO().changePasswordByIdUser(newPassword, currentUserId);
        return isPasswordChanged;
    }

    /***
     * Change password when it was forgotten.
     * <p>
     * This method is executed when a user is trying to recover their password
     * </p>
     * @param email the user's email
     * @param newPassword the new password to be set it
     * @return boolean true if password was changed and false if it was not changed
     * @throws SQLException representing a error in connection to database
     */
    public boolean resetPasswordByUnloggedUser(String email, String newPassword) throws SQLException {
        boolean isPasswordChanged = new AccessAccountDAO().changePasswordByEmail(newPassword, email);
        return isPasswordChanged;
    }

    private Person getCurrentUser(String email, String password) throws UserNotFoundException, SQLException {
        Person person = new PersonDAO().getPersonByEmailAndPassword(email, password);
        if(person == null) {
            throw new UserNotFoundException("¡Revisa tus datos! ¡Puede ser incorrecta el usuario o la contraseña");
        }
        return person;
    }

    private void checkAttemptsLimit() throws LimitReachedException, SQLException {
        final int ATTEMPTS_LIMIT = 5;
        boolean isAttempsLimitReached = new HostDAO().getAttemptsByMacAddress( getMacAddress() ) == ATTEMPTS_LIMIT;
        if(isAttempsLimitReached) {
            throw new LimitReachedException("¡Limite de intentos alcanzado! ¡Espera 10 minutos!");
        }
    }

    private void checkActivityState(Person currentUser) throws InactiveUserException, SQLException {
        ActivityState activityState = new PersonDAO().getActivityStateByID( currentUser.getId() );
        if( activityState == ActivityState.INACTIVE ){
            throw new InactiveUserException("¡Tu estado es inactivo! ¡Contacta con el coordinador o director!");
        }
    }

    private String getMacAddress() {
        String macAddressEncrypted = Cryptography.cryptSHA2( NetworkAddress.getLocalAdress() );
        return macAddressEncrypted;
    }

    private boolean sendMacAddress() throws SQLException {
        boolean isMacAddressSent;
        isMacAddressSent = new HostDAO().sendActualMacAddress( getMacAddress() );
        return isMacAddressSent;
    }

    private boolean resetAttempts() throws SQLException {
        boolean isAttemptsReset;
        isAttemptsReset = new HostDAO().resetAttempts( getMacAddress() );
        return isAttemptsReset;
    }

    private void Auth() {
    }

}
