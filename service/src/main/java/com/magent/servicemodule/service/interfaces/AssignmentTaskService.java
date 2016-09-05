package com.magent.servicemodule.service.interfaces;

import com.magent.domain.AssignmentTask;

import java.util.List;

public interface AssignmentTaskService {

    List<AssignmentTask> getByAssignmentId(Number id);

}
