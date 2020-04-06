package org.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class Security {
    public static final int NAME_LENGTH = 75;
    public static final int EMAIL_LENGTH = 65;
    public static final int NRC_LENGTH = 15;
    public static final int PERIOD_LENGTH = 25;
    public static final int PHONE_NUMBER_LENGTH = 15;
    public static final int ADDRESS_LENGTH = 75;
    public static final int SCHEDULE_LENGTH = 75;
    public static final int CHARGE_RESPONSABLE_LENGTH = 35;
    public static final int ENROLLMENT_LENGTH = 35;
    public static final int COMPANY_ATTRIBUTE_LENGTH = 25;
    public static final String HASH_FUNCTION = "SHA-256";
    public static final String EMAIL_PATTERN = "([A-Za-z0-9]+(\\.?[A-Za-z0-9])*)+@((hotmail|gmail|yahoo)\\.(com|es))+";
    public static final String FREE_PATTERN = "([A-Za-z]{2,}(\\s[A-Za-z]{2,})*)+";
    public static final String DURATION_PATTERN = "^(20|21|22|23|[01]\\d|\\d)((:[0-5]\\d){1,2})$";
    public static final String DATE_PATTERN = "^([2][01]|[1][6-9])\\d{2}\\-([0]\\d|[1][0-2])\\-([0-2]\\d|[3][0-1])(\\s([0-1]\\d|[2][0-3])(\\:[0-5][1-9]){1,2})?$";

    public static boolean isTextValid(String text, int length, String regex){
        boolean band = true;
        if( isStringBigger(text, length) || !verifyCharacters(text, regex) || isStringEmpty(text)) {
            band = false;
        }
        return band;
    }

    public static boolean isStringEmpty(String text) {
        return text.equals("");
    }

    public static boolean verifyCharacters(String string, String pattern){
        return string.matches(pattern);
    }

    public static boolean verifyEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        return ( !isStringBigger(email, EMAIL_LENGTH) && pattern.matcher(email).find());
    }

    private static boolean isStringBigger(String string, int maxLength) {
        return string.length() > maxLength;
    }

    public static boolean verifyDuration(String date) {
        Pattern pattern = Pattern.compile(DURATION_PATTERN);
        return pattern.matcher(date).find();
    }

    public static boolean verifyDateRegistration(LocalDate date) {
        return LocalDate.now().compareTo(date) > -1;
    }

    public static String cryptText(String msg) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(HASH_FUNCTION);
        byte[] array = md.digest(msg.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
        }
        return sb.toString();
    }

}
