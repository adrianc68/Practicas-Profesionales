package org.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypt {
    private static final String HASH_FUNCTION = "SHA-256";

    public static String cryptString(String msg) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(HASH_FUNCTION);
        byte[] array = messageDigest.digest(msg.getBytes());
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            stringBuffer.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
        }
        return stringBuffer.toString();
    }


}
