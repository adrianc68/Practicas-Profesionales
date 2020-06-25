package org.database.dao.hostdao;

import org.database.dao.HostDAO;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.util.Cryptography;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HostDAOTest {

    @Test
    public void getAttemptsByMacAddress() throws SQLException {
        int expected = 2;
        int actual = new HostDAO().getAttemptsByMacAddress( Cryptography.cryptSHA2("343::XX") );
        assertEquals(expected,actual);
    }

    @Test
    public void sendActualMacAddress() throws SQLException {
        boolean isMacAddressSent = new HostDAO().sendActualMacAddress( Cryptography.cryptSHA2("344::XX") );
        assertTrue(isMacAddressSent);
    }

    @Test
    public void resetAttempts() throws SQLException {
        boolean isAttemptsReset = new HostDAO().resetAttempts( Cryptography.cryptSHA2("344::XX") );
        assertTrue(isAttemptsReset);
    }

}