package com.espaciounido.unadcalendar.utils;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;

import java.util.Date;

/**
 * Created by MyMac on 8/09/16.
 */
public abstract class DateUtils {

    public static int restHours(Date dateEnd) {
        return Hours.hoursBetween(new DateTime(new Date()),
                new DateTime(dateEnd)).getHours();
    }

    public static int restHours(Date dateStart, Date dateEnd) {
        return Hours.hoursBetween(new DateTime(dateStart),
                new DateTime(dateEnd)).getHours();
    }

    public static int restDay(Date dateStart, Date dateEnd) {
        return Days.daysBetween(new DateTime(dateStart),
                new DateTime(dateEnd)).getDays();
    }
}
