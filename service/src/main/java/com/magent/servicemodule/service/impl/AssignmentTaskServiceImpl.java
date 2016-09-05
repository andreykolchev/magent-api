package com.magent.servicemodule.service.impl;

import com.magent.domain.AssignmentTask;
import com.magent.repository.AssignmentTaskRepository;
import com.magent.servicemodule.service.interfaces.AssignmentTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AssignmentTaskServiceImpl implements AssignmentTaskService {

    @Autowired
    AssignmentTaskRepository assignmentTaskRepository;

    @Override
    public List<AssignmentTask> getByAssignmentId(Number id) {
        return assignmentTaskRepository.getByAssignmentId(id);
    }

}
