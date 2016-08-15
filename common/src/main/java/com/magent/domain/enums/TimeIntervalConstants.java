package com.magent.domain.enums;

import com.magent.domain.TimeInterval;

import java.util.ArrayList;
import java.util.List;

/**
 * Created  on 02.08.2016.
 * class contains constants for schedule service
 *
 * @see com.magent.service.scheduleservice.SheduleService
 * defaultTimeInterval in format HH:mm
 */
public enum TimeIntervalConstants {
    OTP_INTERVAL_NAME(1, "00:05"),
    TMP_UNREGISTERED_USER_INTERVAL(2, "00:05"),
    BLOCK_INTERVAL(3, "00:02"),
    FORGOT_PASS_INTERVAL(4, "2:00");

    private int id;
    //interval in format HH:mm
    private String defaultTimeInterval;

    TimeIntervalConstants(int id, String defaultTimeInterval) {
        this.id = id;
        this.defaultTimeInterval = defaultTimeInterval;
    }

    public int getId() {
        return id;
    }

    public String getDefaultTimeInterval() {
        return defaultTimeInterval;
    }

    public static List<TimeInterval> getDefaultTimeIntervals() {
        List<TimeInterval> res = new ArrayList<>();
        for (TimeIntervalConstants constants : TimeIntervalConstants.values()) {
            res.add(new TimeInterval(constants));
        }
        return res;
    }

    public static TimeInterval getByName(String name) {
        for (TimeIntervalConstants constants : TimeIntervalConstants.values()) {
            if (constants.toString().equalsIgnoreCase(name)) return new TimeInterval(constants);
        }
        return new TimeInterval(BLOCK_INTERVAL);
    }
}
