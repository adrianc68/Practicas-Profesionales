package org.util;

import org.database.dao.AccessAccountDAO;
import org.database.dao.HostDAO;
import org.database.dao.PersonDAO;
import org.domain.ActivityState;
import org.domain.Person;

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

    public boolean logIn(String email, String password) {
        boolean isLogged = false;
        if( !isAttemptsLimitReached() ) {
            sendMacAddress();
            Person user;
            if( (user = getCurrentUser(email, password)) != null ) {
                if( isActivityStateInActive(user) )
                currentUser = user;
                isLogged = true;
                resetAttempts();
            }
        }
        return isLogged;
    }

    public void logOut() {
        currentUser = null;
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

    private Person getCurrentUser(String email, String password) {
        Person person = new PersonDAO().getPersonByEmailAndPassword(email, password);
        return person;
    }

    private boolean isAttemptsLimitReached() {
        final int ATTEMPTS_LIMIT = 5;
        boolean isAttempsLimitReached = new HostDAO().getAttemptsByMacAddress( getMacAddress() ) == ATTEMPTS_LIMIT;
        return isAttempsLimitReached;
    }

    private boolean isActivityStateInActive(Person currentUser) {
        boolean isActivityStateAsActive = false;
        ActivityState activityState = new PersonDAO().getActivityStateByID( currentUser.getId() );
        if( activityState == ActivityState.ACTIVE ){
            isActivityStateAsActive = true;
        }
        return isActivityStateAsActive;
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

    public boolean generateRecoveryCode(String email) {
        boolean isCodeGenerated = new AccessAccountDAO().generatePasswordRecoveryCodeByEmail( email );
        return isCodeGenerated;
    }

    public String getRecoveryCode(String email) {
        String recoveryCode = new AccessAccountDAO().getRecoveryCodeByEmail(email);
        return recoveryCode;
    }

    private String getMacAddress() {
        String macAddressEncrypted = Cryptography.cryptSHA2( NetworkAddress.getLocalAdress() );
        return macAddressEncrypted;
    }

    private void Auth() {
    }

}
