package com.magent.reportmodule.utils.dateutils;

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
public class DateUtilsImpl implements DateUtils {
    private final DateFormat formatOnlyDays = new SimpleDateFormat("yyyy-MM-dd");
    private final DateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final DateFormat timeStampFormat = new SimpleDateFormat("HH:mm");

    @Override
    public String getOneDayMinus(String date) throws ParseException {
        Calendar calendar = getCalendarOnlyForDays(date);
        calendar.add(Calendar.DATE, -1);
        return formatOnlyDays.format(calendar.getTime());
    }

    @Override
    public String getOneDayPlus(String date) throws ParseException {
        Calendar calendar = getCalendarOnlyForDays(date);
        calendar.add(Calendar.DATE, +1);
        return formatOnlyDays.format(calendar.getTime());
    }

    @Override
    public Date add15Minutes(Date currentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MINUTE, +15);
        return calendar.getTime();
    }

    @Override
    public Date add5Minutes(Date currentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MINUTE, +5);
        return calendar.getTime();
    }

    @Override
    public Date add2Minutes(Date currentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MINUTE, +2);
        return calendar.getTime();
    }

    @Override
    public String formatToSqlDate(Date date) {
        return dbFormat.format(date);
    }
    //use only for native query
    @Override
    public String formatToSqlDateTimeInterval(Date date) {
        return  dbFormat.format(date);
    }

    @Override
    public DateFormat getformatOnlyDays() {
        return formatOnlyDays;
    }

    @Override
    public DateFormat getDbFormat() {
        return dbFormat;
    }

    @Override
    public DateFormat getTimeStampFormat() {
        return timeStampFormat;
    }

    @Override
    public String converToTimeStamp(String timeHHmm, TimeIntervalConstants constants) {
        try {
            Date date = timeStampFormat.parse(timeHHmm);
            Date defaultDate = timeStampFormat.parse(constants.getDefaultTimeInterval());
            return date.getTime() > defaultDate.getTime() ? timeHHmm : constants.getDefaultTimeInterval();

        } catch (Exception e) {
            return constants.getDefaultTimeInterval();
        }
    }


    private Calendar getCalendarOnlyForDays(String date) throws ParseException {
        Date dateFromUser = formatOnlyDays.parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFromUser);
        return calendar;
    }

}
