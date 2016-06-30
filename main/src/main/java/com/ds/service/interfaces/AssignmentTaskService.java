package com.ds.service.interfaces;

import com.ds.domain.AssignmentTask;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AssignmentTaskService {

    List<AssignmentTask> getByAssignmentId(Number id);

}
