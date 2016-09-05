package com.magent.servicemodule.service.impl;

import com.magent.domain.AssignmentAttribute;
import com.magent.repository.AssignmentAttributeRepository;
import com.magent.servicemodule.service.interfaces.AssignmentAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AssignmentAttributeServiceImpl implements AssignmentAttributeService {

    @Autowired
    AssignmentAttributeRepository assignmentAttributeRepository;

    @Override
    public List<AssignmentAttribute> getByAssignmentId(Number id) {

        return assignmentAttributeRepository.getByAssignmentId(id);

    }
}
