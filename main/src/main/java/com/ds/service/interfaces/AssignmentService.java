package com.ds.service.interfaces;

import com.ds.domain.Assignment;
import javassist.NotFoundException;

public interface AssignmentService {


    Assignment createByTemplateId(Assignment assignment) throws NotFoundException;

    Assignment assignToUser(Long assignmentId, Long userId) throws NotFoundException;

}
