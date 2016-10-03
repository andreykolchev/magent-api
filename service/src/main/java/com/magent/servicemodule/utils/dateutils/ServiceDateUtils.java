package com.magent.servicemodule.utils.dateutils;

import com.magent.domain.enums.TimeIntervalConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by artomov.ihor on 15.06.2016.
 */
public interface ServiceDateUtils {

    /**
     *
     * @param date
     * @return minus one day in yyyy-MM-dd
     * @throws ParseException
     */
    String getOneDayMinus(String date) throws ParseException;

    /**
     *
     * @param date
     * @return plus one day in yyyy-MM-dd
     * @throws ParseException
     */
    String getOneDayPlus(String date) throws ParseException;

    /**
     *
     * @param currentDate
     * @return currentDate + 15 Minutes
     */
    Date add15Minutes(Date currentDate);

    /**
     *
     * @param currentDate
     * @return currentDate + 5 Minutes
     */
    Date add5Minutes(Date currentDate);

    /**
     *
     * @param currentDate
     * @return currentDate + 2 Minutes
     */
    Date add2Minutes(Date currentDate);

    /**
     *
     * @param date
     * @return date in SQL format
     */
    String formatToSqlDate(Date date);

    /**
     *
     * @param date
     * @return TimeInterval in SQL format
     */
    String formatToSqlDateTimeInterval(Date date);

    /**
     *
     * @return days
     */
    DateFormat getformatOnlyDays();

    /**
     *
     * @return DB format
     */
    DateFormat getDbFormat();

    /**
     *
     * @return TimeStamp Format
     */
    DateFormat getTimeStampFormat();

    /**
     *
     * @param timeHHmm
     * @param constants
     * @return TimeStamp Format
     */
    String converToTimeStamp(String timeHHmm, TimeIntervalConstants constants);
}
