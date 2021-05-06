package org.util;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CryptographyTest {

    @Test
    public void cryptSHA2() {
        String expected = "b221d9dbb083a7f33428d7c2a3c3198ae925614d70210e28716ccaa7cd4ddb79";
        String actual = Cryptography.cryptSHA2("hola");
        assertEquals(expected, actual);
    }

    @Test
    public void cryptMD5() {
        String expected = "4d186321c1a7f0f354b297e8914ab240";
        String actual = Cryptography.cryptMD5("hola");
        assertEquals(expected, actual);
    }

    @Test
    public void generateRandomPassword() {
        String randomPassword = Cryptography.generateRandomPassword();
        assertNotNull(randomPassword);
    }

}