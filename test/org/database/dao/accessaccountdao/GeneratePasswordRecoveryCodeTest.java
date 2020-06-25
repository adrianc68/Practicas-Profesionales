package org.database.dao.accessaccountdao;

import org.database.dao.AccessAccountDAO;
import org.junit.Test;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeneratePasswordRecoveryCodeTest {

    @Test
    public void generatePasswordRecoveryCodeByEmail() throws SQLException {
        String email = "adf@hotmail.com";
        boolean isCodeGenerated = new AccessAccountDAO().generatePasswordRecoveryCodeByEmail(email);
        assertTrue(isCodeGenerated);
    }

}
