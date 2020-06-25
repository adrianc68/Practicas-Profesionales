package org.database.dao.accessaccountdao;

import org.database.dao.AccessAccountDAO;
import org.junit.Test;
import org.util.Cryptography;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChangePasswordTest {

    @Test
    public void changePasswordByIdUser() throws SQLException {
        String newPassword = Cryptography.cryptSHA2( "NuevaContraseña121302ABC");
        int idUser = 1;
        boolean isPasswordChanged = new AccessAccountDAO().changePasswordByIdUser(newPassword, idUser);
        assertTrue(isPasswordChanged);
    }

    @Test
    public void changePasswordByEmail() throws SQLException {
        String newPassword = Cryptography.cryptSHA2( "Iwontforgetit123");
        String email = "adf@hotmail.com";
        boolean isPasswordChanged = new AccessAccountDAO().changePasswordByEmail(newPassword, email);
        assertTrue(isPasswordChanged);
    }

}