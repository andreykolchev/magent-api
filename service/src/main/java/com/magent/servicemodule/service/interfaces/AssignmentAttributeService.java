package com.magent.servicemodule.service.interfaces;


import com.magent.domain.AssignmentAttribute;

import java.util.List;

public interface AssignmentAttributeService {

    List<AssignmentAttribute> getByAssignmentId(Number id);

}
