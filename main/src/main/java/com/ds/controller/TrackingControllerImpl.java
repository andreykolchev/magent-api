package com.ds.controller;

import com.ds.controller.interfaces.GeneralController;
import com.ds.domain.*;
import com.ds.service.interfaces.TrackingService;
import com.ds.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by kolchev on 11.02.16.
 */
@RestController
@RequestMapping(value = "/tracking")
public class TrackingControllerImpl implements GeneralController {

    @Autowired
    private TrackingService trackingService;

    @Autowired
    private UserService userService;

    /**
     * @return
     */
    @RequestMapping(value = "/settings/", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Settings> getSettings() {
        User activeUser = getActiveUser(userService);
        Settings settings = null;
        if (activeUser != null) {
            settings = trackingService.getSettings(activeUser.getId());
        }
        HttpStatus status = settings == null ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(settings, status);
    }

    /**
     * @param callList
     * @return
     */
    @RequestMapping(value = "/calls/", method = RequestMethod.POST)
    public ResponseEntity createCalls(@RequestBody List<Call> callList) {
        User activeUser = getActiveUser(userService);
        if (activeUser != null) {
            trackingService.createCalls(callList, activeUser.getId());
        }
        HttpStatus status = activeUser == null ? HttpStatus.NOT_FOUND : HttpStatus.CREATED;
        return new ResponseEntity<>(status);
    }

    /**
     * @param location
     * @return
     */
    @RequestMapping(value = "/locations/", method = RequestMethod.POST)
    public ResponseEntity createLocations(@RequestBody Location location) {
        User activeUser = getActiveUser(userService);
        if (activeUser != null) {
            trackingService.createLocations(location, activeUser.getId());
        }
        return getDefaultResponceStatusOnly(activeUser, HttpStatus.CREATED, HttpStatus.NOT_FOUND);
    }

    /**
     * @param activityList
     * @return
     */
    @RequestMapping(value = "/apps/", method = RequestMethod.POST)
    public ResponseEntity createActivities(@RequestBody List<Activity> activityList) {
        User activeUser = getActiveUser(userService);
        if (activeUser != null) {
            trackingService.createActivities(activityList, activeUser.getId());
        }
        return getDefaultResponceStatusOnly(activeUser, HttpStatus.CREATED, HttpStatus.NOT_FOUND);
    }
}
