package com.magent.servicemodule.service.interfaces;

import com.magent.domain.TimeInterval;

/**
 * Created  on 03.08.2016.
 */
public interface TimeIntervalService {
    TimeInterval getByName(String name);

    TimeInterval save(TimeInterval timeInterval);
}
