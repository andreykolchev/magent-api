package com.magent.utils.dateutils;

import com.magent.domain.enums.TimeIntervalConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by artomov.ihor on 15.06.2016.
 */
public interface DateUtils {

    //returns minus one day in yyyy-MM-dd
    String getOneDayMinus(String date) throws ParseException;

    //returns plus one day in yyyy-MM-dd
    String getOneDayPlus(String date) throws ParseException;

    Date add15Minutes(Date currentDate);

    Date add5Minutes(Date currentDate);

    Date add2Minutes(Date currentDate);

    String formatToSqlDate(Date date);

    String formatToSqlDateTimeInterval(Date date);

    DateFormat getformatOnlyDays();

    DateFormat getDbFormat();

    String converToTimeStamp(String timeHHmm, TimeIntervalConstants constants);
}
