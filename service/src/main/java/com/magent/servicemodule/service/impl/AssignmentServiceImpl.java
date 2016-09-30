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

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * service for Assignment CRUD operations
 */
@Service
@Transactional(readOnly = true)
class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    @Qualifier("assignmentGeneralService")
    private GeneralService<Assignment> assignmentGeneralService;

    @Autowired
    @Qualifier("assignmentAttributeGeneralService")
    private GeneralService<AssignmentAttribute> assignmentAttributeGeneralService;

    @Autowired
    @Qualifier("assignmentTaskGeneralService")
    private GeneralService<AssignmentTask> assignmentTaskGeneralService;

    @Autowired
    @Qualifier("assignmentTaskControlGeneralService")
    private GeneralService<AssignmentTaskControl> assignmentTaskControlGeneralService;

    @Autowired
    @Qualifier("assignmentTaskControlServiceImpl")
    private AssignmentTaskControlService assignmentTaskControlService;

    @Autowired
    @Qualifier("templateGeneralService")
    private GeneralService<Template> templateGeneralService;

    @Autowired
    @Qualifier("userGeneralService")
    private GeneralService<User> userGeneralService;

    @Autowired
    private AssignmentRepository assignmentRepository;


    /**
     *
     * @param assignment entity with predefined params
     * @return Assignment entity persisted in DB
     * @throws NotFoundException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Assignment createByTemplateId(Assignment assignment) throws NotFoundException {

        Template template = templateGeneralService.getById(assignment.getTemplateId());
        if (Objects.isNull(template)) {
            throw new NotFoundException("Can't find template");
        }

        Assignment newAssignment = assignmentGeneralService.save(new Assignment(assignment));

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

    /**
     *
     * @param assignmentId id of assignment
     * @param userId id of user
     * @return assignment assigned to another user
     * @throws NotFoundException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Assignment assignToUser(Long assignmentId, Long userId) throws NotFoundException {
        if (Objects.nonNull(userGeneralService.getById(userId))) {
            Assignment assignment = assignmentGeneralService.getById(assignmentId);
            if (Objects.nonNull(assignment)) {
                assignment.setUserId(userId);
                return assignmentRepository.save(assignment);
            }
        }
        throw new NotFoundException("can't find assignment");
    }

}
