package org.util;

import org.database.dao.AccessAccountDAO;
import org.database.dao.HostDAO;
import org.database.dao.PersonDAO;
import org.domain.ActivityState;
import org.domain.Person;
import org.util.exceptions.InactiveUserException;
import org.util.exceptions.LimitReachedException;
import org.util.exceptions.UserNotFoundException;

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

    public boolean logIn(String email, String password) throws UserNotFoundException, LimitReachedException, InactiveUserException {
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

    public void logOut() {
        currentUser = null;
    }

    private Person getCurrentUser(String email, String password) throws UserNotFoundException {
        Person person = new PersonDAO().getPersonByEmailAndPassword(email, password);
        if(person == null) {
            throw new UserNotFoundException("¡Revisa tus datos! ¡Puede ser incorrecta el usuario o la contraseña");
        }
        return person;
    }

    private void checkAttemptsLimit() throws LimitReachedException {
        final int ATTEMPTS_LIMIT = 5;
        boolean isAttempsLimitReached = new HostDAO().getAttemptsByMacAddress( getMacAddress() ) == ATTEMPTS_LIMIT;
        if(isAttempsLimitReached) {
            throw new LimitReachedException("¡Limite de intentos alcanzado! ¡Espera 10 minutos!");
        }
    }

    private void checkActivityState(Person currentUser) throws InactiveUserException {
        ActivityState activityState = new PersonDAO().getActivityStateByID( currentUser.getId() );
        if( activityState == ActivityState.INACTIVE ){
            throw new InactiveUserException("¡Tu estado es inactivo! ¡Contacta con el coordinador o director!");
        }
    }

    private boolean sendMacAddress() {
        boolean isMacAddressSent;
        isMacAddressSent = new HostDAO().sendActualMacAddress( getMacAddress() );
        return isMacAddressSent;
    }

    private boolean resetAttempts() {
        boolean isAttemptsReset;
        isAttemptsReset = new HostDAO().resetAttempts( getMacAddress() );
        return isAttemptsReset;
    }

    private String getMacAddress() {
        String macAddressEncrypted = Cryptography.cryptSHA2( NetworkAddress.getLocalAdress() );
        return macAddressEncrypted;
    }


    public boolean generateRecoveryCode(String email) {
        boolean isCodeGenerated = new AccessAccountDAO().generatePasswordRecoveryCodeByEmail( email );
        return isCodeGenerated;
    }

    public String getRecoveryCode(String email) {
        String recoveryCode = new AccessAccountDAO().getRecoveryCodeByEmail(email);
        return recoveryCode;
    }

    public boolean resetPassword(String newPassword) {
        int currentUserId = Auth.getInstance().getCurrentUser().getId();
        boolean isPasswordChanged = new AccessAccountDAO().changePasswordByIdUser(newPassword, currentUserId);
        return isPasswordChanged;
    }

    public boolean resetPasswordByUnloggedUser(String email, String newPassword) {
        boolean isPasswordChanged = new AccessAccountDAO().changePasswordByEmail(newPassword, email);
        return isPasswordChanged;
    }

    private void Auth() {
    }

}
