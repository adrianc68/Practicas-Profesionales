package org.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cryptography {
    private static final String SHA_256_FUNCTION = "SHA-256";
    private static final String MD5_FUNCTION = "MD5";

    /***
     * This method crypt any text in SHA256
     * <p>
     * It's used when someone needs to crypt some text.
     * In this system, the use that is given is for Auth module.
     * </p>
     * @param string
     * @return String encrypted.
     */
    public static String cryptSHA2(String string) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(SHA_256_FUNCTION);
        } catch (NoSuchAlgorithmException e) {
            Logger.getLogger( Cryptography.class.getName() ).log(Level.SEVERE, null, e);
        }
        byte[] array = messageDigest.digest(string.getBytes());
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            stringBuffer.append( Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3) );
        }
        return stringBuffer.toString();
    }

    /***
     * This method crypt any text in SHA256
     * <p>
     * It's used when someone needs to crypt some text.
     * In this system, the use that is given is for Auth module.
     * </p>
     * @param string
     * @return String encrypted
     */
    public static String cryptMD5(String string) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(MD5_FUNCTION);
        } catch (NoSuchAlgorithmException e) {
            Logger.getLogger( Cryptography.class.getName() ).log(Level.SEVERE, null, e);
        }
        byte[] array = messageDigest.digest(string.getBytes());
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            stringBuffer.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
        }
        return stringBuffer.toString();
    }

    /***
     * This method crypt a random number.
     * <p>
     * The purpose is generate a "random password" encrypted.
     * </p>
     * @return String encrypted in MD5
     */
    public static String generateRandomPassword() {
        return cryptMD5( String.valueOf( Math.random() ) );
    }

}
