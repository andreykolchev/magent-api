package com.ds.service;

import com.ds.domain.AssignmentTask;
import com.ds.repository.AssignmentTaskRepository;
import com.ds.service.interfaces.AssignmentTaskService;
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
