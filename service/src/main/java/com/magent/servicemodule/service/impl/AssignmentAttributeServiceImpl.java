package com.magent.servicemodule.service.impl;

import com.magent.domain.AssignmentAttribute;
import com.magent.repository.AssignmentAttributeRepository;
import com.magent.servicemodule.service.interfaces.AssignmentAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * service for  AssignmentAttribute CRUD operations
 */
@Service
@Transactional(readOnly = true)
class AssignmentAttributeServiceImpl implements AssignmentAttributeService {

    @Autowired
    AssignmentAttributeRepository assignmentAttributeRepository;

    /**
     *
     * @param id Assignment id
     * @return list of AssignmentAttributes by Assignment id
     * @see AssignmentAttribute
     */
    @Override
    public List<AssignmentAttribute> getByAssignmentId(Number id) {

        return assignmentAttributeRepository.getByAssignmentId(id);

    }
}
