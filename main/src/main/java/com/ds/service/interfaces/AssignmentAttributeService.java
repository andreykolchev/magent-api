package com.ds.service.interfaces;


import com.ds.domain.AssignmentAttribute;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AssignmentAttributeService {

    List<AssignmentAttribute> getByAssignmentId(Number id);

}
