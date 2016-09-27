package com.magent.controller;

import com.magent.controller.interfaces.GeneralController;
import com.magent.domain.Device;
import com.magent.servicemodule.service.interfaces.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for creating device
 * Created by lutay.d on 09.12.2015.
 */
@RestController
@RequestMapping("/devices")
public class DeviceControllerImpl implements GeneralController {

    @Autowired
    private DeviceService deviceService;

    /**
     * @param device Device entity in json format
     * @return id of created Device entity with HttpStatus.Ok if created else NOT_FOUND status
     * @see Device class for more details
     */

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<String> addOrUpdateDevice(@RequestBody Device device) {
        Device currentDevice = deviceService.addOrUpdateDevice(device);
        return getDefaultResponce(currentDevice.getId(), HttpStatus.OK, HttpStatus.NOT_FOUND);
    }
}
