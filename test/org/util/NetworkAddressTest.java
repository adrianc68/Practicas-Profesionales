package org.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NetworkAddressTest {

    @Test
    void getLocalAdress() {
        String expected = "185fcc620946da8dd2dc7d4115dabd7f35d9c06f0772411edb18e8bd488c549d";
        String actual = Cryptography.cryptSHA2( NetworkAddress.getLocalAdress() );
        assertEquals(expected, actual);
    }
}