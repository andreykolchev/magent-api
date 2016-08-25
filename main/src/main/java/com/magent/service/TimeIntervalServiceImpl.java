package com.magent.service;

import com.magent.domain.TimeInterval;
import com.magent.domain.enums.TimeIntervalConstants;
import com.magent.repository.TimeIntervalRepository;
import com.magent.service.interfaces.TimeIntervalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Created  on 03.08.2016.
 */
@Service
public class TimeIntervalServiceImpl implements TimeIntervalService {
    @Autowired
    private TimeIntervalRepository timeIntervalRepository;

    @Override
    @Transactional(readOnly = true)
    public TimeInterval getByName(String name) {
            TimeInterval timeInterval= timeIntervalRepository.getByName(name);
            if (Objects.nonNull(timeInterval))return timeInterval;
            return TimeIntervalConstants.getByName(name);

    }
}
