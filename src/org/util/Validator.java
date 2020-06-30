package org.util;

public class Validator {
    public static final int NAME_LENGTH = 75;
    public static final int EMAIL_LENGTH = 65;
    public static final int NRC_LENGTH = 15;
    public static final int PERIOD_LENGTH = 25;
    public static final int PHONE_NUMBER_LENGTH = 15;
    public static final int ADDRESS_LENGTH = 75;
    public static final int SCHEDULE_LENGTH = 75;
    public static final int CHARGE_RESPONSABLE_LENGTH = 35;
    public static final int ENROLLMENT_LENGTH = 35;
    public static final int LARGE_TEXT_LENGTH = 1500;
    public static final int STATE_LENGTH = 75;
    public static final int CITY_LENGTH = 65;
    public static final int STAFF_NUMBER_LENGTH = 25;
    public static final int PROJECT_MULTIVALUED_ATTRIBUTES_LENGTH = 35;
    public static  final int DURATION_LENGTH = 5;
    public static final int NUMBER_LENGTH = 11;
    public static final int RECOVERY_CODE_LENGTH = 8;
    public static final int TIME_LENGTH = 10;
    public static final int PATH_LENGTH = 550;
    public static final String NAME_AND_NUMBERS_PATTERN = "([A-Za-z0-9.-áéíóúüÁÉÍÓÚÜñÑ]{1,}(\\ [A-Za-z0-9.-áéíóúüÁÉÍÓÚÜñÑ]{1,})*)";
    public static final String RECOVERY_CODE_PATTERN = "[a-zA-Z0-9áéíóúüÁÉÍÓÚÜñÑ]{8}";
    public static final String STAFF_NUMBER_PATTERN = "([a-zA-Z0-9\\-]{3,})";
    public static final String ENROLLMENT_PATTERN = "S([\\d]{8})";
    public static final String PHONE_NUMBER_PATTERN = "([\\d]){8,10}";
    public static final String ADDRESS_PATTERN = "[a-zA-Z0-9áéíóúüÁÉÍÓÚÜñÑ.#,]{2,}+(\\ ([a-zA-Z0-9áéíóúüÁÉÍÓÚÜñÑ.#,]{1,}))*";
    public static final String CITY_PATTERN = "[a-zA-ZáéíóúüÁÉÍÓÚÜñÑ]{2,}+(\\ ([a-zA-ZáéíóúüÁÉÍÓÚÜñÑ]{1,}))*";
    public static final String STATE_PATTERN = "[a-zA-ZáéíóúüÁÉÍÓÚÜñÑ]{2,}+(\\ ([a-zA-ZáéíóúüÁÉÍÓÚÜñÑ]{1,}))*";
    public static final String PROJECT_NAME_PATTERN = "[a-zA-ZáéíóúüÁÉÍÓÚÜñÑ]{2,}+(\\ ([a-zA-ZáéíóúüÁÉÍÓÚÜñÑ]{1,}))*";
    public static final String PROJECT_MULTIVALUED_ATTRIBUTE_PATTERN = "[a-zA-ZáéíóúüÁÉÍÓÚÜñÑ]{2,}+(\\ ([a-zA-ZáéíóúüÁÉÍÓÚÜñÑ]{1,}))*";
    public static final String NAME_PATTERN = "([A-Za-záéíóúüÁÉÍÓÚÜñÑ]{2,}(\\ [A-Za-záéíóúüÁÉÍÓÚÜñÑ]{2,})*)";
    public static final String LARGE_TEXT_PATTERN = "([A-Za-z0-9.,-áéíóúüÁÉÍÓÚÜñÑ\\r\\n]{1,}(\\ [A-Za-z0-9.,-áéíóúüÁÉÍÓÚÜñÑ\\r\\n]{1,})*)";
    public static final String CHARGE_RESPONSABLE_PATTERN = "[a-zA-ZáéíóúüÁÉÍÓÚÜñÑ]{2,}+(\\ ([a-zA-ZáéíóúüÁÉÍÓÚÜñÑ]{1,}))*";
    public static final String EMAIL_PATTERN = "[A-Za-z0-9\\-ñÑ]{1,}(\\.([A-Za-z0-9\\-ñÑ]{1,}))*@(([a-zA-Z]{2,})(\\.([a-z]{1,})){1,})";
    public static final String SCHEDULE_PATTERN = "[a-zA-Z0-9:-áéíóúüÁÉÍÓÚÜñÑ]{1,}+(\\ ([a-zA-Z0-9:-áéíóúüÁÉÍÓÚÜñÑ]{1,}))*";
    public static final String DURATION_PATTERN = "(([1-9]{1,3}[0]*)(\\.[\\d]{1,})?)";
    public static final String NUMBER_PATTERN = "([0-9]{1,})";
    public static final String NRC_PATTERN = "([0-9]{5,10})";
    public static final String PERIOD_PATTERN = "(([A-Z]{3}\\ [0-9]{4})\\ \\-\\ ([A-Z]{3}\\ [0-9]{4}))";
    public static final String DATE_PATTERN = "(([0-9]){1,2}/([0-9]){1,2}/([0-9]){4})";
    public static final String TIME_PATTERN = "([0-9]{1,2})";
    public static final String PATH_PATTERN = "([a-zA-Z0-9áéíóúüÁÉÍÓÚÜñÑí\\s\\_:`´,.;/\\\\]{2,})";

    /***
     * This method search for a match with a pattern
     * <p>
     * The purpose of this method it's try to match a text to a specified pattern
     * </p>
     * @param string
     * @param pattern
     * @return
     */
    public static boolean doesStringMatchPattern(String string, String pattern) {
        boolean stringMatchWithPattern = string.matches(pattern);
        return stringMatchWithPattern;
    }

    /***
     * This method check if a string is larger than a specified limit.
     * <p>
     * The purpose of tihs method it's try to match a text to a specified amount of characters
     * </p>
     * @param string
     * @param limit
     * @return
     */
    public static boolean isStringLargerThanLimitOrEmpty(String string, int limit) {
        boolean isStringEmptyOrLarger = string.length() > limit || string.equals("");
        return isStringEmptyOrLarger;
    }

}
