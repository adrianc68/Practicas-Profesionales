package org.util;

import org.junit.Test;
import java.sql.Date;
import java.sql.Timestamp;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class DateFormatterTest {

    @Test
    public void getDateTimeByString() {
        Date date = DateFormatter.getDateTimeByString("2020-06-26 12:30:00");
        assertNotNull(date);
    }

    @Test
    public void getDateTimeByInvalidString() {
        Date date = DateFormatter.getDateTimeByString("TX");
        assertNull(date);
    }

    @Test
    public void getTimeStampByString() {
        Timestamp timestamp = DateFormatter.getTimeStampByString("2020-06-26 12:30:00");
        assertNotNull(timestamp);
    }

    @Test
    public void getTimeStampByInvalidString() {
        Timestamp timestamp = DateFormatter.getTimeStampByString("T2");
        assertNull(timestamp);
    }

}