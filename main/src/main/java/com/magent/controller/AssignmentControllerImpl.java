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

import java.util.List;

/**
 * controller for working with assignment entity and related entities
 */
@RestController
@RequestMapping("/assignments")
public class AssignmentControllerImpl implements GeneralController {

    @Autowired
    @Qualifier("assignmentGeneralService")
    private GeneralService<Assignment> assignmentGeneralService;

    @Autowired
    @Qualifier("assignmentServiceImpl")
    private AssignmentService assignmentService;

    @Autowired
    @Qualifier("assignmentAttributeGeneralService")
    private GeneralService<AssignmentAttribute> assignmentAttributeGeneralService;

    @Autowired
    @Qualifier("assignmentAttributeServiceImpl")
    private AssignmentAttributeService assignmentAttributeService;

    @Autowired
    @Qualifier("assignmentTaskGeneralService")
    private GeneralService<AssignmentTask> assignmentTaskGeneralService;

    @Autowired
    @Qualifier("assignmentTaskServiceImpl")
    private AssignmentTaskService assignmentTaskService;

    @Autowired
    @Qualifier("assignmentTaskControlGeneralService")
    private GeneralService<AssignmentTaskControl> assignmentTaskControlGeneralService;

    @Autowired
    private AssignmentTaskControlService assignmentTaskControlService;


    /**
     * @return List of Assignment from database table ma_assignment
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Assignment> get() {
        return assignmentGeneralService.getAll();
    }

    /**
     * @param assignment Assignment entity in json format with id null
     * @return same assignment if it created
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Assignment> create(@RequestBody Assignment assignment) {
        Assignment newAssignment = assignmentGeneralService.save(assignment);
        return new ResponseEntity<>(newAssignment, HttpStatus.CREATED);
    }

    /**
     * @param assignment  assignment entity in json format without null id
     * @return  same assignment if current updated
     * @throws NotFoundException - if can't find current entity by id in database
     */
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<Assignment> update(@RequestBody Assignment assignment) throws NotFoundException {
        Assignment updateAssignment = assignmentGeneralService.update(assignment, assignment.getId());
        return new ResponseEntity<>(updateAssignment, HttpStatus.OK);
    }

    /**
     * @param id entity id in database
     * @return Assignment entity with current id
     * @throws NotFoundException - if can't find entity by current param
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Assignment> getById(@PathVariable("id") Long id) throws NotFoundException {
        Assignment assignment = assignmentGeneralService.getById(id);
        return new ResponseEntity<>(assignment, HttpStatus.OK);
    }

    /**
     * @param id entity's id which present in database and going to be removed
     * @return HttpStatus.OK if service didn't throw any exception
     * @see com.magent.controller.loggerlevel.LoggerController
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteAssignmentById(@PathVariable("id") Long id) {
        assignmentGeneralService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /*-------------------------------------Assignment attributes---------------------------------------*/

    /**
     * @param assignId  Assignment id
     * @return  list of AssignmentAttribute which have same assignmentId variable as have id param
     * @see Assignment
     * @see AssignmentAttribute
     */
    @RequestMapping(value = "/{assignId}/attributes/", method = RequestMethod.GET)
    public ResponseEntity<List<AssignmentAttribute>> getAttributesByAssignmentId(@PathVariable("assignId") Long assignId) {
        List<AssignmentAttribute> assignmentAttributesList = assignmentAttributeService.getByAssignmentId(assignId);
        return new ResponseEntity<>(assignmentAttributesList, HttpStatus.OK);
    }

    /**
     * @param assignId            Assignment id
     * @param assignmentAttribute AssignmentAttribute in json format which going to be stored in database
     * @return - AssignmentAttribute if it saved in database
     */
    @RequestMapping(value = "/{assignId}/attributes/", method = RequestMethod.POST)
    public ResponseEntity<AssignmentAttribute> createAttribute(
            @PathVariable("assignId") Long assignId,
            @RequestBody AssignmentAttribute assignmentAttribute) {
        AssignmentAttribute newAssignmentAttribute = assignmentAttributeGeneralService.save(assignmentAttribute);
        return getDefaultResponce(newAssignmentAttribute, HttpStatus.CREATED, HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * @param assignId            Assignment id
     * @param assignmentAttribute AssignmentAttribute in json format which going to be updated in database
     * @return same AssignmentAttribute if it updated
     * @throws NotFoundException - if can't find current entity by id in database
     */
    @RequestMapping(value = "/{assignId}/attributes/", method = RequestMethod.PUT)
    public ResponseEntity<AssignmentAttribute> updateAttribute(
            @PathVariable("assignId") Long assignId,
            @RequestBody AssignmentAttribute assignmentAttribute) throws NotFoundException {
        AssignmentAttribute updateAssignmentAttribute = assignmentAttributeGeneralService.update(assignmentAttribute, assignmentAttribute.getId());
        return new ResponseEntity<>(updateAssignmentAttribute, HttpStatus.OK);
    }

    /**
     * @param assignId Assignment id
     * @param attrId   AssignmentAttribute id
     * @return AssignmentAttribute which has attrId as primary key
     * @throws NotFoundException - if can't find entity in database with current id
     */
    @RequestMapping(value = "/{assignId}/attributes/{attrId}", method = RequestMethod.GET)
    public ResponseEntity<AssignmentAttribute> getAttributeById(
            @PathVariable("assignId") Long assignId,
            @PathVariable("attrId") Long attrId) throws NotFoundException {
        AssignmentAttribute assignmentAttribute = assignmentAttributeGeneralService.getById(attrId);
        return new ResponseEntity<>(assignmentAttribute, HttpStatus.OK);
    }

    /**
     * @param attrId AssignmentAttribute id variable
     * @return return HttpStatus.OK if delete method won't throw any exception
     */
    @RequestMapping(value = "/attributes/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteAssignmentAttributeById(@PathVariable("id") Long attrId) {
        assignmentAttributeGeneralService.delete(attrId);
        return new ResponseEntity(HttpStatus.OK);
    }

    /*-------------------------------------Assignment Tasks---------------------------------------*/

    /**
     * @param id Assignment id
     * @return list of AssignmentTask which have same assignmentId variable as have id param
     */
    @RequestMapping(value = "/{assignId}/tasks/", method = RequestMethod.GET)
    public ResponseEntity<List<AssignmentTask>> getTasksByAssignmentId(@PathVariable("assignId") Long id) {
        List<AssignmentTask> assignmentTaskList = assignmentTaskService.getByAssignmentId(id);
        return new ResponseEntity<>(assignmentTaskList, HttpStatus.OK);
    }

    /**
     * @param assignmentId   Assignment id
     * @param assignmentTask AssignmentTask in json format
     * @return AssignmentTask if it saved
     */
    @RequestMapping(value = "/{assignId}/tasks/", method = RequestMethod.POST)
    public ResponseEntity<AssignmentTask> createTask(@PathVariable("assignId") Long assignmentId,
                                                     @RequestBody AssignmentTask assignmentTask) {
        assignmentTask.setAssignmentId(assignmentId);
        AssignmentTask newAssignmentTask = assignmentTaskGeneralService.save(assignmentTask);
        return getDefaultResponce(newAssignmentTask, HttpStatus.CREATED, HttpStatus.NOT_FOUND);
    }

    /**
     * @param assignmentId   Assignment id
     * @param assignmentTask AssignmentTask in json format
     * @return AssignmentTask if updated
     * @throws NotFoundException - if can't find entity in database
     */

    @RequestMapping(value = "/{assignId}/tasks/", method = RequestMethod.PUT)
    public ResponseEntity<AssignmentTask> updateTask(@PathVariable("assignId") Long assignmentId,
                                                     @RequestBody AssignmentTask assignmentTask) throws NotFoundException {
        assignmentTask.setAssignmentId(assignmentId);
        AssignmentTask updateAssignmentTask = assignmentTaskGeneralService.update(assignmentTask, assignmentTask.getId());
        return getDefaultResponce(updateAssignmentTask, HttpStatus.OK, HttpStatus.NOT_FOUND);
    }

    /**
     * @param assignId  Assignment id
     * @param taskId  AssignmentTask id
     * @return - AssignmentTask which have same assignmentId variable as have id param
     * @throws NotFoundException - if can't find AssignmentTask with current id
     */

    @RequestMapping(value = "/{assignId}/tasks/{taskId}/", method = RequestMethod.GET)
    public ResponseEntity<AssignmentTask> getTaskById(@PathVariable("assignId") Long assignId,
                                                      @PathVariable("taskId") Long taskId) throws NotFoundException {
        AssignmentTask assignmentTask = assignmentTaskGeneralService.getById(taskId);
        return new ResponseEntity<>(assignmentTask, HttpStatus.OK);
    }

    /**
     * @param id - AssignmentTask id which present in database and going to be removed
     * @return - HttpStatus.OK if service didn't throw any exception
     */
    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteAssignmentTaskById(@PathVariable("id") Long id) {
        assignmentTaskGeneralService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /*-------------------------------------Assignment Controls---------------------------------------*/

    /**
     * @param taskId AssignmentTask id
     * @return list of AssignmentTaskControl which have assignmentTaskId variable
     */
    @RequestMapping(value = "/tasks/{taskId}/controls/", method = RequestMethod.GET)
    public ResponseEntity<List<AssignmentTaskControl>> getControlsByTaskId(@PathVariable("taskId") Long taskId) {
        List<AssignmentTaskControl> assignmentTaskControlList = assignmentTaskControlService.getByTaskId(taskId);
        return new ResponseEntity<>(assignmentTaskControlList, HttpStatus.OK);
    }

    /**
     * @param taskId                AssignmentTask id
     * @param assignmentTaskControl AssignmentTaskControl in json format
     * @return AssignmentTaskControl if it saved
     */
    @RequestMapping(value = "/tasks/{taskId}/controls/", method = RequestMethod.POST)
    public ResponseEntity<AssignmentTaskControl> createControl(@PathVariable("taskId") int taskId,
                                                               @RequestBody AssignmentTaskControl assignmentTaskControl) {
        AssignmentTaskControl newAssignmentTaskControl = assignmentTaskControlGeneralService.save(assignmentTaskControl);
        return getDefaultResponce(newAssignmentTaskControl, HttpStatus.CREATED, HttpStatus.NOT_FOUND);
    }

    /**
     * @param taskId                AssignmentTask id
     * @param assignmentTaskControl AssignmentTaskControl in json format which going to be updated
     * @return HttpStatus.OK if service didn't throw any exception
     * @throws NotFoundException - if entity has id with null value
     */
    @RequestMapping(value = "/tasks/{taskId}/controls/", method = RequestMethod.PUT)
    public ResponseEntity updateControl(@PathVariable("taskId") int taskId,
                                        @RequestBody AssignmentTaskControl assignmentTaskControl) throws NotFoundException {
        AssignmentTaskControl updateAssignmentTaskControl =
                assignmentTaskControlGeneralService.update(assignmentTaskControl, assignmentTaskControl.getId());
        return new ResponseEntity<>(updateAssignmentTaskControl, HttpStatus.OK);
    }

    /**
     * @param taskId    AssignmentTask id
     * @param controlId AssignmentTaskControl id
     * @return AssignmentTaskControl entity which have param controlId
     * @throws NotFoundException if can't find entity by controlId
     */

    @RequestMapping(value = "/tasks/{taskId}/controls/{controlId}", method = RequestMethod.GET)
    public ResponseEntity<AssignmentTaskControl> getControlById(@PathVariable("taskId") Long taskId,
                                                                @PathVariable("controlId") Long controlId) throws NotFoundException {
        AssignmentTaskControl assignmentTaskControl = assignmentTaskControlGeneralService.getById(controlId);
        return new ResponseEntity<>(assignmentTaskControl, HttpStatus.OK);
    }

    /**
     * @param controlId AssignmentTaskControl id
     * @return return HttpStatus.OK if delete method won't throw any exception
     */
    @RequestMapping(value = "/tasks/controls/{controlId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteAssignmentTaskControlById(@PathVariable("controlId") Long controlId) {
        assignmentTaskControlGeneralService.delete(controlId);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     *
     * @param assignment Assignment entity in json format
     * @return Assignment created by templateId variable which include in assignment param
     * @throws NotFoundException if can't find template by id which has Assignment entity
     */
    @RequestMapping(value = "/createByTemplateId", method = RequestMethod.POST)
    public ResponseEntity<Assignment> createByTemplateId(@RequestBody Assignment assignment) throws NotFoundException {
        Assignment newAssignment = assignmentService.createByTemplateId(assignment);
        return getDefaultResponce(newAssignment, HttpStatus.CREATED, HttpStatus.NOT_FOUND);
    }

    /**
     * method reassignment Assignment entity to User
     * @param assignmentId Assignment id
     * @param userId User id from database
     * @return  Assignment if it reassigned
     * @throws NotFoundException - if can't find User or Assignment
     */
    @RequestMapping(value = "/{assignId}/assignToUser/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<Assignment> assignToUser(@PathVariable("assignId") Long assignmentId,
                                                   @PathVariable("userId") Long userId) throws NotFoundException {
        Assignment assignment = assignmentService.assignToUser(assignmentId, userId);
        return new ResponseEntity<>(assignment, HttpStatus.OK);
    }


}