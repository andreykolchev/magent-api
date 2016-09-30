package com.magent.servicemodule.service.impl;

import com.magent.domain.Activity;
import com.magent.domain.Call;
import com.magent.domain.Location;
import com.magent.domain.Settings;
import com.magent.repository.ActivityRepository;
import com.magent.repository.CallRepository;
import com.magent.repository.LocationRepository;
import com.magent.repository.SettingsRepository;
import com.magent.servicemodule.service.interfaces.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * service for TrackingService operations.
 */
@Service
@Transactional(readOnly = true)
class TrackingServiceImpl implements TrackingService {

    @Autowired
    private SettingsRepository settingsRepository;

    @Autowired
    private CallRepository callRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ActivityRepository activityRepository;

    /**
     *
     * @param userId
     * @return Settings for current user of mobile application
     */
    @Override
    public Settings getSettings(Long userId) {
        return settingsRepository.findByUserId(userId);
    }

    /**
     * save calls statistics for current user of mobile application
     * @param callList list of Call
     * @param userId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createCalls(List<Call> callList, Long userId) {
        List<Call> calls = callList.stream().map(call -> {
            call.setUserId(userId);
            return call;
        }).collect(Collectors.toList());
        callRepository.save(calls);
    }

    /**
     * save locations statistics for current user of mobile application
     * @param location Location object
     * @param userId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createLocations(Location location, Long userId) {
        location.setUserId(userId);
        locationRepository.save(location);
    }

    /**
     * save applications activity statistics for current user of mobile application
     * @param activityList
     * @param userId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createActivities(List<Activity> activityList, Long userId) {
        List<Activity> activities = activityList.stream().map(activity -> {
            activity.setUserId(userId);
            return activity;
        }).collect(Collectors.toList());
        activityRepository.save(activities);
    }


}
