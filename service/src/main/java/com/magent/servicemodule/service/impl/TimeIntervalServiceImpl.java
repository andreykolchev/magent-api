package com.magent.servicemodule.service.impl;

import com.magent.domain.TimeInterval;
import com.magent.domain.enums.TimeIntervalConstants;
import com.magent.repository.TimeIntervalRepository;
import com.magent.servicemodule.service.interfaces.TimeIntervalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Created  on 03.08.2016.
 */
@Service
class TimeIntervalServiceImpl implements TimeIntervalService {
    @Autowired
    private TimeIntervalRepository timeIntervalRepository;

    @Override
    @Transactional(readOnly = true)
    public TimeInterval getByName(String name) {
        TimeInterval timeInterval = timeIntervalRepository.getByName(name);
        if (Objects.nonNull(timeInterval)) return timeInterval;
        return TimeIntervalConstants.getByName(name);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TimeInterval save(TimeInterval timeInterval) {
        return timeIntervalRepository.save(timeInterval);
    }
}
