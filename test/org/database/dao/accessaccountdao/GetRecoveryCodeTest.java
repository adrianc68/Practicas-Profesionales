package org.database.dao.accessaccountdao;

import org.database.dao.AccessAccountDAO;
import org.junit.Test;
import java.sql.SQLException;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetRecoveryCodeTest {

    @Test
    public void getRecoveryCodeByEmail() throws SQLException {
        String recoveryCode;
        String email = "adf@hotmail.com";
        recoveryCode = new AccessAccountDAO().getRecoveryCodeByEmail(email);
        assertNotNull(recoveryCode);
    }

    @Test
    public void getRecoveryCodeByUnexistingEmail() throws SQLException {
        String recoveryCode;
        String email = "thisemaildoesntexit@hotmail.com";
        recoveryCode = new AccessAccountDAO().getRecoveryCodeByEmail(email);
        assertNull(recoveryCode);
    }



}
