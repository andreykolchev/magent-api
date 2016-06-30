package com.ds.service.interfaces;

import com.ds.domain.AssignmentTaskControl;

import java.util.List;

public interface AssignmentTaskControlService {

    List<AssignmentTaskControl> getByTaskId(Number id);

}
