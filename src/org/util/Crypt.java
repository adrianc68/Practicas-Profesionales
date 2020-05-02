package org.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypt {
    private static final String HASH_FUNCTION = "SHA-256";

    public static String cryptString(String msg) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(HASH_FUNCTION);
        byte[] array = md.digest(msg.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
        }
        return sb.toString();
    }


}
