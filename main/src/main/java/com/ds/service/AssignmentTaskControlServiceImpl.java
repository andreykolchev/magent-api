package com.ds.service;

import com.ds.domain.AssignmentTaskControl;
import com.ds.repository.AssignmentTaskControlRepository;
import com.ds.service.interfaces.AssignmentTaskControlService;
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
