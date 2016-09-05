package com.magent.servicemodule.service.impl;

import com.magent.domain.*;
import com.magent.repository.AssignmentRepository;
import com.magent.servicemodule.service.interfaces.AssignmentService;
import com.magent.servicemodule.service.interfaces.AssignmentTaskControlService;
import com.magent.servicemodule.service.interfaces.GeneralService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    @Qualifier("assignmentGeneralService")
    GeneralService assignmentGeneralService;

    @Autowired
    @Qualifier("assignmentAttributeGeneralService")
    GeneralService assignmentAttributeGeneralService;

    @Autowired
    @Qualifier("assignmentTaskGeneralService")
    GeneralService assignmentTaskGeneralService;

    @Autowired
    @Qualifier("assignmentTaskControlGeneralService")
    GeneralService assignmentTaskControlGeneralService;

    @Autowired
    @Qualifier("assignmentTaskControlServiceImpl")
    AssignmentTaskControlService assignmentTaskControlService;

    @Autowired
    @Qualifier("templateGeneralService")
    GeneralService templateGeneralService;

    @Autowired
    @Qualifier("userGeneralService")
    GeneralService userGeneralService;

    @Autowired
    AssignmentRepository assignmentRepository;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Assignment createByTemplateId(Assignment assignment) throws NotFoundException {

        Template template = (Template) templateGeneralService.getById(assignment.getTemplateId());
        if (Objects.isNull(template)) {
            throw new IllegalArgumentException("Can't find template");
        }

        Assignment newAssignment = (Assignment) assignmentGeneralService.save(new Assignment(assignment));

        newAssignment.setAttributes(
                template.getAttributes().stream().map(templateAttribute -> {
                    AssignmentAttribute attribute = new AssignmentAttribute(newAssignment.getId(), templateAttribute);
                    assignmentAttributeGeneralService.save(attribute);
                    return attribute;
                }).collect(Collectors.toSet())
        );

        newAssignment.setTasks(
                template.getTemplateTasks().stream().map(templateTask -> {
                    AssignmentTask assignmentTask = new AssignmentTask(newAssignment.getId(), templateTask);
                    assignmentTaskGeneralService.save(assignmentTask);
                    assignmentTask.setControls(
                            templateTask.getControls().stream().map(templateTaskControl -> {
                                AssignmentTaskControl assignmentTaskControl = new AssignmentTaskControl(assignmentTask.getId(), templateTaskControl);
                                assignmentTaskControlGeneralService.save(assignmentTaskControl);
                                return assignmentTaskControl;
                            }).collect(Collectors.toSet())
                    );
                    return assignmentTask;
                }).collect(Collectors.toSet())
        );

        return newAssignment;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Assignment assignToUser(Long assignmentId, Long userId) throws NotFoundException {
        if (Objects.nonNull(userGeneralService.getById(userId))) {
            Assignment assignment = (Assignment) assignmentGeneralService.getById(assignmentId);
            if (Objects.nonNull(assignment)) {
                assignment.setUserId(userId);
                return assignmentRepository.save(assignment);
            }
        }
        throw new NotFoundException("can't find assignment");
    }

}
