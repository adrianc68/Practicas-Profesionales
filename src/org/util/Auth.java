package org.util;

import org.database.dao.PersonDAO;
import org.domain.Person;

public class Auth {
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

    public void login() {
        PersonDAO personDAO = new PersonDAO();
        user = personDAO.getPersonByEmailAndPassword(email, password);
    }

    private void Auth() {
    }
}
