package com.ds.service.interfaces;

import com.ds.domain.Activity;
import com.ds.domain.Call;
import com.ds.domain.Location;
import com.ds.domain.Settings;

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
