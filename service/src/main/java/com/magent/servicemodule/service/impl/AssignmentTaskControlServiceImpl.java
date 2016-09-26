package com.magent.servicemodule.service.impl;

import com.magent.domain.AssignmentTaskControl;
import com.magent.repository.AssignmentTaskControlRepository;
import com.magent.servicemodule.service.interfaces.AssignmentTaskControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
class AssignmentTaskControlServiceImpl implements AssignmentTaskControlService {

    @Autowired
    AssignmentTaskControlRepository assignmentTaskControlRepository;

    @Override
    public List<AssignmentTaskControl> getByTaskId(Number id) {
        return assignmentTaskControlRepository.getByAssignmentTaskId(id);
    }
}
