package com.ds.controller;

import com.ds.controller.interfaces.GeneralController;
import com.ds.domain.Device;
import com.ds.service.interfaces.DeviceService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lutay.d on 09.12.2015.
 */
@RestController
@RequestMapping("/devices")
public class DeviceControllerImpl implements GeneralController {

    @Autowired
    private DeviceService deviceService;

    /**
     * @param device
     * @return
     */

    @SuppressFBWarnings("NP_NULL_ON_SOME_PATH")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<String> addOrUpdateDevice(@RequestBody Device device) {
        Device currentDevice = deviceService.addOrUpdateDevice(device);
        return getDefaultResponce(currentDevice.getId(), HttpStatus.OK, HttpStatus.NOT_FOUND);
    }
}
