package com.magent.servicemodule.service.interfaces;

import com.magent.domain.Activity;
import com.magent.domain.Call;
import com.magent.domain.Location;
import com.magent.domain.Settings;

import java.util.List;

/**
 * Created by kolchev on 11.02.16.
 */
public interface TrackingService {

    Settings getSettings(Long userId);

    void createCalls(List<Call> callList, Long userId);

    void createLocations(Location location, Long userId);

    void createActivities(List<Activity> activityList, Long userId);

}
