package org.util;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.util.exceptions.InactiveUserException;
import org.util.exceptions.LimitReachedException;
import org.util.exceptions.UserNotFoundException;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthTest {

    @Test
    public void generateRecoveryCode() throws SQLException {
        String email = "angelsj@uv.com.mx";
        boolean isCodeGenerated = Auth.getInstance().generateRecoveryCode(email);
        assertTrue(isCodeGenerated);
    }

    @Test
    public void generateRecoveryCodeByNoExistingEmail() throws SQLException {
        String email = "";
        boolean isCodeGenerated = Auth.getInstance().generateRecoveryCode(email);
        assertFalse(isCodeGenerated);
    }

    @Test
    public void getRecoveryCode() throws SQLException {
        String recoveryCode = Auth.getInstance().getRecoveryCode("angelsj@uv.com.mx");
        assertNotNull(recoveryCode);
    }

    @Test
    public void getRecoveryCodeByNoExistingEmail() throws SQLException {
        String recoveryCode = Auth.getInstance().getRecoveryCode("");
        assertNull(recoveryCode);
    }

    @Test
    public void logIn() throws UserNotFoundException, SQLException, LimitReachedException, InactiveUserException {
        String email = "angelsj@uv.com.mx";
        String password = Cryptography.cryptSHA2("NuevaContraseña12302ABC");
        boolean isLogged = Auth.getInstance().logIn(email, password);
        assertTrue(isLogged);
    }

    @Test
    public void logInByNoExistingEmail() {
        String email = "";
        String password = Cryptography.cryptSHA2("NuevaContraseña12302ABC");
        Class<UserNotFoundException> expected = UserNotFoundException.class;
        Class<? extends UserNotFoundException> exceptionActual = assertThrows(UserNotFoundException.class, () -> Auth.getInstance().logIn(email, password) ).getClass();
        assertEquals(expected, exceptionActual );
    }

    @Test
    public void logInAccountInStateInactive() {
        String email = "asdf@hotmail.com";
        String password = Cryptography.cryptSHA2("hola");
        Class<? extends InactiveUserException> actual = InactiveUserException.class;
        Class<? extends InactiveUserException> exceptionActual = assertThrows(InactiveUserException.class, () -> Auth.getInstance().logIn(email, password) ).getClass();
        assertEquals(actual, exceptionActual);
    }

    @Test
    public void resetPassword() throws UserNotFoundException, SQLException, LimitReachedException, InactiveUserException {
        String email = "angelsj@uv.com.mx";
        String password = Cryptography.cryptSHA2("NuevaContraseña12302ABC");
        String newPassword = Cryptography.cryptSHA2("NuevaContraseña12302ABC");
        Auth.getInstance().logIn(email, password);
        boolean expected = true;
        boolean actual = Auth.getInstance().resetPassword(newPassword);
        assertEquals(expected, actual);
    }

    @Test
    public void resetPasswordByUnloggedUser() throws SQLException {
        String email = "francv@uv.com.mx";
        String newPassword = "rocaMaster32";
        boolean isPasswordChanged = Auth.getInstance().resetPasswordByUnloggedUser(email, newPassword);
        assertTrue(isPasswordChanged);
    }

}