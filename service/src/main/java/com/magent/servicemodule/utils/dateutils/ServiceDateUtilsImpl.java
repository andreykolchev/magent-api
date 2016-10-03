package com.magent.servicemodule.utils.dateutils;

import com.magent.domain.enums.TimeIntervalConstants;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by artomov.ihor on 15.06.2016.
 */
@Component
class ServiceDateUtilsImpl implements ServiceDateUtils {
    private final DateFormat formatOnlyDays = new SimpleDateFormat("yyyy-MM-dd");
    private final DateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final DateFormat timeStampFormat = new SimpleDateFormat("HH:mm");

    /**
     * @param date
     * @return date minus one day in yyyy-MM-dd
     * @throws ParseException if getCalendarOnlyForDays() throws exception
     * @see #getCalendarOnlyForDays(String)
     */
    @Override
    public String getOneDayMinus(String date) throws ParseException {
        Calendar calendar = getCalendarOnlyForDays(date);
        calendar.add(Calendar.DATE, -1);
        return formatOnlyDays.format(calendar.getTime());
    }

    /**
     * @param date
     * @return date plus one day in yyyy-MM-dd
     * @throws ParseException if getCalendarOnlyForDays() throws exception
     * @see #getCalendarOnlyForDays(String)
     */
    @Override
    public String getOneDayPlus(String date) throws ParseException {
        Calendar calendar = getCalendarOnlyForDays(date);
        calendar.add(Calendar.DATE, +1);
        return formatOnlyDays.format(calendar.getTime());
    }

    /**
     * @param currentDate
     * @return currentDate + 15 Minutes
     */
    @Override
    public Date add15Minutes(Date currentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MINUTE, +15);
        return calendar.getTime();
    }

    /**
     * @param currentDate
     * @return currentDate + 5 Minutes
     */
    @Override
    public Date add5Minutes(Date currentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MINUTE, +5);
        return calendar.getTime();
    }

    /**
     * @param currentDate
     * @return currentDate + 2 Minutes
     */
    @Override
    public Date add2Minutes(Date currentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MINUTE, +2);
        return calendar.getTime();
    }

    /**
     * @param date
     * @return date in SQL format
     */
    @Override
    public String formatToSqlDate(Date date) {
        return dbFormat.format(date);
    }

    /**
     * use only for native query
     * Formats a Date into a date/time string.
     *
     * @param date
     * @return date in SQL format
     */
    @Override
    public String formatToSqlDateTimeInterval(Date date) {
        return dbFormat.format(date);
    }

    /**
     * @return only days format (yyyy-MM-dd)
     */
    @Override
    public DateFormat getformatOnlyDays() {
        return formatOnlyDays;
    }

    /**
     * @return DB format (yyyy-MM-dd HH:mm:ss)
     */
    @Override
    public DateFormat getDbFormat() {
        return dbFormat;
    }

    /**
     * @return time stamp format (HH:mm)
     */
    @Override
    public DateFormat getTimeStampFormat() {
        return timeStampFormat;
    }

    /**
     * @param timeHHmm
     * @param constants
     * @return time stamp format (HH:mm) maximum of two values: timeHHmm, constants
     */
    @Override
    public String convertToTimeStamp(String timeHHmm, TimeIntervalConstants constants) {
        try {
            Date date = timeStampFormat.parse(timeHHmm);
            Date defaultDate = timeStampFormat.parse(constants.getDefaultTimeInterval());
            return date.getTime() > defaultDate.getTime() ? timeHHmm : constants.getDefaultTimeInterval();

        } catch (Exception e) {
            return constants.getDefaultTimeInterval();
        }
    }

    /**
     * @param date
     * @return Calendar with time setting
     * @throws ParseException if formatOnlyDays.parse(date) throws exception
     */
    private Calendar getCalendarOnlyForDays(String date) throws ParseException {
        Date dateFromUser = formatOnlyDays.parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFromUser);
        return calendar;
    }

}
