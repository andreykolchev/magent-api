package com.magent.service;

import com.magent.domain.AssignmentTaskControl;
import com.magent.repository.AssignmentTaskControlRepository;
import com.magent.service.interfaces.AssignmentTaskControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class AssignmentTaskControlServiceImpl implements AssignmentTaskControlService {

    @Autowired
    AssignmentTaskControlRepository assignmentTaskControlRepository;

    @Override
    public List<AssignmentTaskControl> getByTaskId(Number id) {
        return assignmentTaskControlRepository.getByAssignmentTaskId(id);
    }
}
