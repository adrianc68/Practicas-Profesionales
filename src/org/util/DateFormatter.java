package org.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateFormatter {
    /***
     * Get datetime in sql by a string.
     * <p>
     * This method it's used when you need to format a string to a type date
     * for example: string -> 2020-06-26 12:30:00
     * This method will return a type Date -> 2020-06-26
     * </p>
     * @return Date representing the date of string.
     */
    public static Date getDateTimeByString(String date) {
        Date dateSQL = null;
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            dateSQL = new Date( simpleDateFormat.parse(date).getTime() );
        } catch (ParseException e) {
            Logger.getLogger( DateFormatter.class.getName() ).log(Level.WARNING, null, e);
        }
        return dateSQL;
    }

    /***
     * Get Timestamp by a string.
     * <p>
     * This method it's used when you need to format a string to a type timestamp
     * for example: string -> 2020-06-26 12:30:00
     * This method will return a type timestamp -> 2020-06-26 12:30:00
     * </p>
     * @return Timestamp representing the date of string.
     */
    public static Timestamp getTimeStampByString(String date) {
        Timestamp timestamp = null;
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            timestamp = new Timestamp( simpleDateFormat.parse(date).getTime() );
        } catch (ParseException e) {
            Logger.getLogger( DateFormatter.class.getName() ).log(Level.WARNING, null, e);
        }
        return timestamp;
    }

}
