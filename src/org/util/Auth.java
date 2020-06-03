package org.util;

import org.database.dao.AccessAccountDAO;
import org.database.dao.HostDAO;
import org.database.dao.PersonDAO;
import org.domain.Person;

public class Auth {
    private static final int ATTEMPTS_LIMIT = 5;
    private static Auth instance;
    private Person user;
    private String email;
    private String password;

    public static Auth getInstance() {
        if(instance == null) {
            instance = new Auth();
        }
        return instance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Person getUser() {
        return user;
    }

    public void logIn(String macAddress, String email, String password) {
        HostDAO hostDAO = new HostDAO();
        hostDAO.sendActualMacAddress(macAddress);
        user = new PersonDAO().getPersonByEmailAndPassword(email, password);
        if(user != null) {
            hostDAO.resetAttempts(macAddress);
        }
    }

    public void logOut() {
        user = null;
        email = null;
        password = null;
    }

    public boolean isAttempsLimitReached(String macAddress) {
        return new HostDAO().getAttemptsByMacAddress(macAddress) == ATTEMPTS_LIMIT;
    }

    public boolean generateRecoveryCode(String email) {
        return new AccessAccountDAO().generatePasswordRecoveryCodeByEmail( email );
    }

    public String getRecoveryCode(String email) {
        return new AccessAccountDAO().getRecoveryCodeByEmail(email);
    }

    private void Auth() {
    }

}
