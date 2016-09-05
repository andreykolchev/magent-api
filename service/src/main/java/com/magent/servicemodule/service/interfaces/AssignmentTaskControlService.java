package com.magent.servicemodule.service.interfaces;

import com.magent.domain.AssignmentTaskControl;

import java.util.List;

public interface AssignmentTaskControlService {

    List<AssignmentTaskControl> getByTaskId(Number id);

}
