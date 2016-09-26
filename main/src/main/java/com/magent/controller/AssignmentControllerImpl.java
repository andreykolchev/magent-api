package com.magent.controller;

import com.magent.controller.interfaces.GeneralController;
import com.magent.domain.Assignment;
import com.magent.domain.AssignmentAttribute;
import com.magent.domain.AssignmentTask;
import com.magent.domain.AssignmentTaskControl;
import com.magent.servicemodule.service.interfaces.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/assignments")
public class AssignmentControllerImpl implements GeneralController {

    @Autowired
    @Qualifier("assignmentGeneralService")
    GeneralService assignmentGeneralService;


    @Autowired
    @Qualifier("assignmentServiceImpl")
    AssignmentService assignmentService;

    @Autowired
    @Qualifier("assignmentAttributeGeneralService")
    GeneralService assignmentAttributeGeneralService;

    @Autowired
    @Qualifier("assignmentAttributeServiceImpl")
    AssignmentAttributeService assignmentAttributeService;

    @Autowired
    @Qualifier("assignmentTaskGeneralService")
    GeneralService assignmentTaskGeneralService;

    @Autowired
    @Qualifier("assignmentTaskServiceImpl")
    AssignmentTaskService assignmentTaskService;

    @Autowired
    @Qualifier("assignmentTaskControlGeneralService")
    GeneralService assignmentTaskControlGeneralService;

    @Autowired
    @Qualifier("assignmentTaskControlServiceImpl")
    AssignmentTaskControlService assignmentTaskControlService;


    /**
     * @return
     */

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Assignment> get() throws NotFoundException {
        return assignmentGeneralService.getAll();
    }

    /**
     * @param assignment
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Assignment> create(@RequestBody Assignment assignment) {
        Assignment newAssignment = (Assignment) assignmentGeneralService.save(assignment);
        return new ResponseEntity<>(newAssignment, HttpStatus.CREATED);
    }

    /**
     * @param assignment
     * @return
     * @throws NotFoundException
     */
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<Assignment> update(@RequestBody Assignment assignment) throws NotFoundException {
        Assignment updateAssignment = (Assignment) assignmentGeneralService.update(assignment, assignment.getId());
        return new ResponseEntity<>(updateAssignment, HttpStatus.OK);
    }

    /**
     * @param id
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Assignment> getById(@PathVariable("id") Long id) throws NotFoundException {
        Assignment assignment = (Assignment) assignmentGeneralService.getById(id);
        return new ResponseEntity<>(assignment, HttpStatus.OK);
    }

    /**
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteAssignmentById(@PathVariable("id") Long id) {
        assignmentGeneralService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /*-------------------------------------Assignment attributes---------------------------------------*/

    /**
     * @param assignId
     * @return
     */
    @RequestMapping(value = "/{assignId}/attributes/", method = RequestMethod.GET)
    public ResponseEntity<List<AssignmentAttribute>> getAttributesByAssignmentId(@PathVariable("assignId") Long assignId) {
        List<AssignmentAttribute> assignmentAttributesList = assignmentAttributeService.getByAssignmentId(assignId);
        return new ResponseEntity<>(assignmentAttributesList, HttpStatus.OK);
    }

    /**
     * @param assignId
     * @param assignmentAttribute
     * @return
     */
    @RequestMapping(value = "/{assignId}/attributes/", method = RequestMethod.POST)
    public ResponseEntity<AssignmentAttribute> createAttribute(
            @PathVariable("assignId") Long assignId,
            @RequestBody AssignmentAttribute assignmentAttribute) {
        AssignmentAttribute newAssignmentAttribute = (AssignmentAttribute) assignmentAttributeGeneralService.save(assignmentAttribute);
        return getDefaultResponce(newAssignmentAttribute,HttpStatus.CREATED , HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * @param assignId
     * @param assignmentAttribute
     * @return
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{assignId}/attributes/", method = RequestMethod.PUT)
    public ResponseEntity<AssignmentAttribute> updateAttribute(
            @PathVariable("assignId") Long assignId,
            @RequestBody AssignmentAttribute assignmentAttribute) throws NotFoundException {
        AssignmentAttribute updateAssignmentAttribute = (AssignmentAttribute) assignmentAttributeGeneralService.update(assignmentAttribute, assignmentAttribute.getId());
        return new ResponseEntity<>(updateAssignmentAttribute, HttpStatus.OK);
    }

    /**
     * @param assignId
     * @param attrId
     * @return
     */
    @RequestMapping(value = "/{assignId}/attributes/{attrId}", method = RequestMethod.GET)
    public ResponseEntity<AssignmentAttribute> getAttributeById(
            @PathVariable("assignId") Long assignId,
            @PathVariable("attrId") Long attrId) throws NotFoundException {
        AssignmentAttribute assignmentAttribute = (AssignmentAttribute) assignmentAttributeGeneralService.getById(attrId);
        return new ResponseEntity<>(assignmentAttribute, HttpStatus.OK);
    }

    /**
     * @param attrId
     * @return
     */
    @RequestMapping(value = "/attributes/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteAssignmentAttributeById(@PathVariable("id") Long attrId) {
        assignmentAttributeGeneralService.delete(attrId);
        return new ResponseEntity(HttpStatus.OK);
    }

    /*-------------------------------------Assignment Tasks---------------------------------------*/

    /**
     * @param id
     * @return
     */
    @RequestMapping(value = "/{assignId}/tasks/", method = RequestMethod.GET)
    public ResponseEntity<List<AssignmentTask>> getTasksByAssignmentId(@PathVariable("assignId") Long id) {
        List<AssignmentTask> assignmentTaskList = assignmentTaskService.getByAssignmentId(id);
        return new ResponseEntity<>(assignmentTaskList, HttpStatus.OK);
    }

    /**
     * @param assignmentId
     * @param assignmentTask
     * @return
     */
    @RequestMapping(value = "/{assignId}/tasks/", method = RequestMethod.POST)
    public ResponseEntity<AssignmentTask> createTask(@PathVariable("assignId") Integer assignmentId,
                                                     @RequestBody AssignmentTask assignmentTask) {
        AssignmentTask newAssignmentTask = (AssignmentTask) assignmentTaskGeneralService.save(assignmentTask);
        return getDefaultResponce(newAssignmentTask,HttpStatus.CREATED , HttpStatus.NOT_FOUND);
    }

    /**
     * @param assignmentId
     * @param assignmentTask
     * @return
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{assignId}/tasks/", method = RequestMethod.PUT)
    public ResponseEntity<AssignmentTask> updateTask(@PathVariable("assignId") Long assignmentId,
                                                     @RequestBody AssignmentTask assignmentTask) throws NotFoundException {
        AssignmentTask updateAssignmentTask = (AssignmentTask) assignmentTaskGeneralService.update(assignmentTask, assignmentTask.getId());
        return getDefaultResponce(updateAssignmentTask,HttpStatus.OK , HttpStatus.NOT_FOUND);
    }

    /**
     * @param assignId
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/{assignId}/tasks/{taskId}/", method = RequestMethod.GET)
    public ResponseEntity<AssignmentTask> getTaskById(@PathVariable("assignId") Long assignId,
                                                      @PathVariable("taskId") Long taskId) throws NotFoundException {
        AssignmentTask assignmentTask = (AssignmentTask) assignmentTaskGeneralService.getById(taskId);
        return new ResponseEntity<>(assignmentTask, HttpStatus.OK);
    }

    /**
     * @param id
     * @return
     */
    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteAssignmentTaskById(@PathVariable("id") Long id) {
        assignmentTaskGeneralService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /*-------------------------------------Assignment Controls---------------------------------------*/

    /**
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/tasks/{taskId}/controls/", method = RequestMethod.GET)
    public ResponseEntity<List<AssignmentTaskControl>> getControlsByTaskId(@PathVariable("taskId") Long taskId) {
        List<AssignmentTaskControl> assignmentTaskControlList = assignmentTaskControlService.getByTaskId(taskId);
        return new ResponseEntity<>(assignmentTaskControlList, HttpStatus.OK);
    }

    /**
     * @param taskId
     * @param assignmentTaskControl
     * @return
     */
    @RequestMapping(value = "/tasks/{taskId}/controls/", method = RequestMethod.POST)
    public ResponseEntity<AssignmentTaskControl> createControl(@PathVariable("taskId") int taskId,
                                                               @RequestBody AssignmentTaskControl assignmentTaskControl) {
        AssignmentTaskControl newAssignmentTaskControl = (AssignmentTaskControl) assignmentTaskControlGeneralService.save(assignmentTaskControl);
        return getDefaultResponce(newAssignmentTaskControl,HttpStatus.CREATED , HttpStatus.NOT_FOUND);
    }

    /**
     * @param taskId
     * @param assignmentTaskControl
     * @return
     * @throws NotFoundException
     */
    @RequestMapping(value = "/tasks/{taskId}/controls/", method = RequestMethod.PUT)
    public ResponseEntity updateControl(@PathVariable("taskId") int taskId,
                                        @RequestBody AssignmentTaskControl assignmentTaskControl) throws NotFoundException {
        AssignmentTaskControl updateAssignmentTaskControl =
                (AssignmentTaskControl) assignmentTaskControlGeneralService.update(assignmentTaskControl, assignmentTaskControl.getId());
        return new ResponseEntity<>(updateAssignmentTaskControl, HttpStatus.OK);
    }

    /**
     * @param taskId
     * @param controlId
     * @return
     */
    @RequestMapping(value = "/tasks/{taskId}/controls/{controlId}", method = RequestMethod.GET)
    public ResponseEntity<AssignmentTaskControl> getControlById(@PathVariable("taskId") Long taskId,
                                                                @PathVariable("controlId") Long controlId) throws NotFoundException {
        AssignmentTaskControl assignmentTaskControl = (AssignmentTaskControl) assignmentTaskControlGeneralService.getById(controlId);
        return new ResponseEntity<>(assignmentTaskControl, HttpStatus.OK);
    }

    /**
     * @param controlId
     * @return
     */
    @RequestMapping(value = "/tasks/controls/{controlId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteAssignmentTaskControlById(@PathVariable("controlId") Long controlId) {
        assignmentTaskControlGeneralService.delete(controlId);
        return new ResponseEntity(HttpStatus.OK);
    }

    /*----------------------------------------------------------------------------*/

    /**
     * @param assignment
     * @return
     */
    @RequestMapping(value = "/createByTemplateId", method = RequestMethod.POST)
    public ResponseEntity<Assignment> createByTemplateId(@RequestBody Assignment assignment) throws NotFoundException {
        Assignment newAssignment = assignmentService.createByTemplateId(assignment);
        return getDefaultResponce(newAssignment,HttpStatus.CREATED , HttpStatus.NOT_FOUND);
    }

    /**
     * @param assignmentId
     * @param userId
     * @return
     */
    @RequestMapping(value = "/{assignId}/assignToUser/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<Assignment> assignToUser(@PathVariable("assignId") Long assignmentId,
                                                   @PathVariable("userId") Long userId) throws NotFoundException {
        Assignment assignment = assignmentService.assignToUser(assignmentId, userId);
        return new ResponseEntity<>(assignment, HttpStatus.OK);
    }


}