package com.magent.servicemodule.service.impl;


import com.magent.domain.Device;
import com.magent.domain.User;
import com.magent.repository.DeviceRepository;
import com.magent.servicemodule.service.interfaces.DeviceService;
import com.magent.servicemodule.service.interfaces.GeneralService;
import com.magent.servicemodule.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * service for Device operations
 */
@Service
class DeviceServiceImpl implements DeviceService {

    @Autowired
    @Qualifier("userGeneralService")
    GeneralService userGeneralService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceRepository deviceRepository;

    /**
     *
     * @param device Device entity
     * @return Device entity persisted in DB
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Device addOrUpdateDevice (Device device){
        User activeUser = userService.findByLogin((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Device currentDevice = insertOrLoad(device);
        currentDevice.addUser(activeUser);
        activeUser.addDevice(currentDevice);
        userGeneralService.save(activeUser);
        return currentDevice;
    }

    /**
     * transactional method
     * @param device  Device entity
     * @return Device entity persisted in DB
     */
    @Transactional(rollbackFor = Exception.class)
    public Device insertOrLoad(Device device) {
        Device persistedDevice = deviceRepository.findOne(device.getId());
        if (persistedDevice == null) {
            return deviceRepository.saveAndFlush(device);
        } else {
            if (device.getGoogleId()!=null && !device.getGoogleId().equals(persistedDevice.getGoogleId())) {
                persistedDevice.setGoogleId(device.getGoogleId());
                persistedDevice = deviceRepository.save(persistedDevice);
            }
            return persistedDevice;
        }
    }
}
