package org.util;

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
    public static final int LARGE_TEXT_LENGTH = 180;
    public static final int STATE_LENGTH = 75;
    public static final int CITY_LENGTH = 65;
    public static final int STAFF_NUMBER_LENGTH = 25;
    public static final int PROJECT_MULTIVALUED_ATTRIBUTES_LENGTH = 35;
    public static  final int DURATION_LENGTH = 3;
    public static final String PROJECT_NAME_PATTERN = "([A-Za-z]{2,}((\\s[A-Za-z]{2,})?)+)+";
    public static final String PROJECT_MULTIVALUED_ATTRIBUTE_PATTERN = "([A-Za-z]{2,}((\\s[A-Za-z]{2,})?)+)+";
    public static final String NAME_PATTERN = "([A-Za-z]{2,}(\\s[A-Za-z]{2,})+)";
    public static final String EMAIL_PATTERN = "([A-Za-z0-9]+(\\.?[A-Za-z0-9])*)+@((hotmail|gmail|yahoo|estudiantes)\\.(com|es|uv.mx))+";
    public static final String ENROLLMENT_PATTERN = "S([\\d]{8})";
    public static final String PHONE_NUMBER_PATTERN = "([\\d]){8,10}";
    public static final String LARGE_TEXT_PATTERN = "([A-Za-z,.]{2,}(\\s[A-Za-z]{2,})*)+";
    public static final String DATE_PATTERN = "^([2][01]|[1][6-9])\\d{2}\\-([0]\\d|[1][0-2])\\-([0-2]\\d|[3][0-1])(\\s([0-1]\\d|[2][0-3])(\\:[0-5][1-9]){1,2})?$";
    public static final String SCHEDULE_PATTERN = "([\\dA-Za-z]{1,}(\\s[\\dA-Za-z]{1,})+)";
    public static final String CHARGE_RESPONSABLE_PATTERN = "([A-Za-z]{1,}((\\s[A-Za-z]){1,})?)+";
    public static final String DURATION_PATTERN = "([1-9]{1,3}[0]*)";

    public static boolean doesStringMatchPattern(String string, String pattern) {
        return string.matches(pattern);
    }

    public static boolean isStringLargerThanLimitOrEmpty(String string, int limit) {
        return ( string.length() > limit || string.equals("") );
    }

}
