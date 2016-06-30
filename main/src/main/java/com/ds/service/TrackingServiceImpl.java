package com.ds.service;

import com.ds.domain.Activity;
import com.ds.domain.Call;
import com.ds.domain.Location;
import com.ds.domain.Settings;
import com.ds.repository.ActivityRepository;
import com.ds.repository.CallRepository;
import com.ds.repository.LocationRepository;
import com.ds.repository.SettingsRepository;
import com.ds.service.interfaces.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class TrackingServiceImpl implements TrackingService {

    @Autowired
    private SettingsRepository settingsRepository;

    @Autowired
    private CallRepository callRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ActivityRepository activityRepository;


    @Override
    public Settings getSettings(Long userId) {
        return settingsRepository.findByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createCalls(List<Call> callList, Long userId) {
        List<Call> calls = callList.stream().map(call -> {
            call.setUserId(userId);
            return call;
        }).collect(Collectors.toList());
        callRepository.save(calls);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createLocations(Location location, Long userId) {
        location.setUserId(userId);
        locationRepository.save(location);
    }

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
