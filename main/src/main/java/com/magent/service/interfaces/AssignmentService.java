package com.magent.service.interfaces;

import com.magent.domain.Assignment;
import javassist.NotFoundException;

public interface AssignmentService {


    Assignment createByTemplateId(Assignment assignment) throws NotFoundException;

    Assignment assignToUser(Long assignmentId, Long userId) throws NotFoundException;

}
