package org.util;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ValidatorTest {

    @Test
    public void doesStringMatchPattern() {
        boolean expected = false;
        String text = "abcdefghijklmnopqrstuvwxyz";
        boolean actual = Validator.doesStringMatchPattern(text, Validator.NAME_PATTERN);
        assertEquals(expected, actual);
    }

    @Test
    public void doesStringMatchPatternNumberPattern() {
        boolean expected = false;
        String text = "abcdefghijklmnopqrstuvwxyz";
        boolean actual = Validator.doesStringMatchPattern(text, Validator.NUMBER_PATTERN);
        assertEquals(expected, actual);
    }

    @Test
    public void doesStringMatchPatternEmailPattern() {
        boolean expected = false;
        String text = "abcdefghijklmnopqrstuvwxyz";
        boolean actual = Validator.doesStringMatchPattern(text, Validator.EMAIL_PATTERN);
        assertEquals(expected, actual);
    }

    @Test
    public void doesStringMatchPatternPhoneNumberPattern() {
        boolean expected = false;
        String text = "abcdefghijklmnopqrstuvwxyz";
        boolean actual = Validator.doesStringMatchPattern(text, Validator.PHONE_NUMBER_PATTERN);
        assertEquals(expected, actual);
    }

    @Test
    public void doesStringMatchPatternStaffNumberPattern() {
        boolean expected = true;
        String text = "abcdefghijklmnopqrstuvwxyz";
        boolean actual = Validator.doesStringMatchPattern(text, Validator.STAFF_NUMBER_PATTERN);
        assertEquals(expected, actual);
    }

    @Test
    public void doesStringMatchPatternDurationPattern() {
        boolean expected = false;
        String text = "abcdefghijklmnopqrstuvwxyz";
        boolean actual = Validator.doesStringMatchPattern(text, Validator.DURATION_PATTERN);
        assertEquals(expected, actual);
    }

    @Test
    public void doesStringMatchPatternNRCPattern() {
        boolean expected = false;
        String text = "abcdefghijklmnopqrstuvwxyz";
        boolean actual = Validator.doesStringMatchPattern(text, Validator.NRC_PATTERN);
        assertEquals(expected, actual);
    }

    @Test
    public void doesStringMatchPatternSchedulePattern() {
        boolean expected = true;
        String text = "abcdefghijklmnopqrstuvwxyz";
        boolean actual = Validator.doesStringMatchPattern(text, Validator.SCHEDULE_PATTERN);
        assertEquals(expected, actual);
    }

    @Test
    public void doesStringMatchPatternLargeTextPattern() {
        boolean expected = true;
        String text = "abcdefghijklmnopqrstuvwxyz";
        boolean actual = Validator.doesStringMatchPattern(text, Validator.LARGE_TEXT_PATTERN);
        assertEquals(expected, actual);
    }

    @Test
    public void doesStringMatchPatternAddressPattern() {
        boolean expected = true;
        String text = "abcdefghijklmnopqrstuvwxyz";
        boolean actual = Validator.doesStringMatchPattern(text, Validator.ADDRESS_PATTERN);
        assertEquals(expected, actual);
    }

    @Test
    public void doesStringMatchPatternPeriodPattern() {
        boolean expected = false;
        String text = "abcdefghijklmnopqrstuvwxyz";
        boolean actual = Validator.doesStringMatchPattern(text, Validator.PERIOD_PATTERN);
        assertEquals(expected, actual);
    }

    @Test
    public void doesStringMatchPatternProjectAttributePattern() {
        boolean expected = true;
        String text = "abcdefghijklmnopqrstuvwxyz";
        boolean actual = Validator.doesStringMatchPattern(text, Validator.PROJECT_MULTIVALUED_ATTRIBUTE_PATTERN);
        assertEquals(expected, actual);
    }

    @Test
    public void isStringLargerThanLimitOrEmptyShortLimit() {
        String text = "abcdefghijklmnopqrstuvwxyz";
        int textLimit = 5;
        boolean expected = true;
        boolean actual = Validator.isStringLargerThanLimitOrEmpty(text, textLimit);
        assertEquals(expected, actual);
    }

    @Test
    public void isStringLargerThanLimitOrEmptyLongLimit() {
        String text = "abcdefghijklmnopqrstuvwxyz";
        int textLimit = 28;
        boolean expected = false;
        boolean actual = Validator.isStringLargerThanLimitOrEmpty(text, textLimit);
        assertEquals(expected, actual);
    }

}