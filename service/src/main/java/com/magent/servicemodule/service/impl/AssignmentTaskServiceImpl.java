package com.magent.servicemodule.service.impl;

import com.magent.domain.AssignmentTask;
import com.magent.repository.AssignmentTaskRepository;
import com.magent.servicemodule.service.interfaces.AssignmentTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * service for AssignmentTask operations
 */
@Service
@Transactional(readOnly = true)
class AssignmentTaskServiceImpl implements AssignmentTaskService {

    @Autowired
    AssignmentTaskRepository assignmentTaskRepository;

    /**
     * @param id Assignment id
     * @return list of AssignmentTask by Assignment id
     * @see com.magent.domain.Assignment
     */
    @Override
    public List<AssignmentTask> getByAssignmentId(Number id) {
        return assignmentTaskRepository.getByAssignmentId(id);
    }

}
