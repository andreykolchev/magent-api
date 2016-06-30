package com.ds.controller;

import com.ds.controller.interfaces.GeneralController;
import com.ds.domain.Template;
import com.ds.domain.TemplateAttribute;
import com.ds.domain.TemplateTask;
import com.ds.domain.TemplateTaskControl;
import com.ds.service.interfaces.GeneralService;
import com.ds.service.interfaces.TemplateAttributeService;
import com.ds.service.interfaces.TemplateTaskControlService;
import com.ds.service.interfaces.TemplateTaskService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/templates")
public class TemplateControllerImpl implements GeneralController {

    @Autowired
    @Qualifier("templateGeneralService")
    private GeneralService templateGeneralService;

    @Autowired
    @Qualifier("templateAttributeGeneralService")
    private GeneralService templateAttributeGenService;

    @Autowired
    @Qualifier("templateAttributeServiceImpl")
    private TemplateAttributeService templateAttributeService;

    @Autowired
    @Qualifier("templateTaskGeneralService")
    private GeneralService templateTaskGenService;

    @Autowired
    @Qualifier("templateTaskServiceImpl")
    private TemplateTaskService templateTaskService;

    @Autowired
    @Qualifier("templateTaskControlGeneralService")
    GeneralService templateTaskControlGeneralService;

    @Autowired
    @Qualifier("templateTaskControlServiceImpl")
    TemplateTaskControlService templateTaskControlService;

    /**
     * @return - list of Template
     * @see Template class enums
     */

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Template> get() throws NotFoundException {
        return templateGeneralService.getAll();
    }

    /**
     * @param template - Template enums without id
     * @return - created Template enums
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Template> create(@RequestBody Template template) {
        Template newTemplate = (Template) templateGeneralService.save(template);
        return getDefaultResponce(newTemplate,HttpStatus.CREATED , HttpStatus.NOT_FOUND);
    }

    /**
     * @param template - Template enums with id
     * @return - updated Template enums
     * @throws NotFoundException if id not present or enums not present in db
     */
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<Template> update(@RequestBody Template template) throws NotFoundException {
        Template updatedTemplate = (Template) templateGeneralService.update(template, template.getId());
        return getDefaultResponce(updatedTemplate,HttpStatus.OK , HttpStatus.NOT_FOUND);

    }

    /**
     * @param id - of enums
     * @return - Template enums which has this id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Template> getTemplateById(@PathVariable("id") Long id) throws NotFoundException {
        Template template = (Template) templateGeneralService.getById(id);
        return new ResponseEntity<>(template, HttpStatus.OK);
    }

    /**
     * Important information template can be deleted if it don't have any assignments
     *
     * @param id - of enums
     * @return HttpStatus
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTemplateById(@PathVariable("id") Long id) {
        templateGeneralService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /*-------------------------------------Template attributes---------------------------------------*/

    /**
     * @param templId - id from Template class
     * @return - TemplateAttribute enums which has current template.id
     */
    @RequestMapping(value = "/{templId}/attributes/", method = RequestMethod.GET)
    public ResponseEntity<List<TemplateAttribute>> getAttributesByTemplateId(
            @PathVariable("templId") Long templId) {
        List<TemplateAttribute> templateAttributes = templateAttributeService.getAttributesByTemplateId(templId);
        return getDefaultResponce(templateAttributes,HttpStatus.OK , HttpStatus.NOT_FOUND);
    }

    /**
     * @param templId           - id from Template enums
     * @param templateAttribute - TemplateAttribute enums which will be created without id
     * @return - created TemplateAttribute
     */
    @RequestMapping(value = "/{templId}/attributes/", method = RequestMethod.POST)
    public ResponseEntity<TemplateAttribute> createAttribute(
            @PathVariable("templId") Long templId,
            @RequestBody TemplateAttribute templateAttribute) {
        templateAttribute.setTemplateId(templId);
        TemplateAttribute newTemplateAttribute = (TemplateAttribute) templateAttributeGenService.save(templateAttribute);
        return getDefaultResponce(newTemplateAttribute,HttpStatus.CREATED , HttpStatus.NOT_FOUND);
    }

    /**
     * @param templId           - id from Template enums
     * @param templateAttribute - TemplateAttribute enums which will be updated with id
     * @return - updated TemplateAttribute enums
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{templId}/attributes/", method = RequestMethod.PUT)
    public ResponseEntity updateAttribute(
            @PathVariable("templId") Long templId,
            @RequestBody TemplateAttribute templateAttribute) throws NotFoundException {
        templateAttribute.setTemplateId(templId);
        TemplateAttribute updated = (TemplateAttribute) templateAttributeGenService.update
                (templateAttribute, templateAttribute.getId());
        return getDefaultResponce(updated,HttpStatus.OK , HttpStatus.NOT_FOUND);

    }

    /**
     * @param templId - id from Template enums
     * @param attrId  - id from TemplateAttribute enums
     * @return TemplateAttribute with current attrId
     */
    @RequestMapping(value = "/{templId}/attributes/{attrId}", method = RequestMethod.GET)
    public ResponseEntity<TemplateAttribute> getAttributeById(
            @PathVariable("templId") Long templId,
            @PathVariable("attrId") Long attrId) throws NotFoundException {
        TemplateAttribute templateAttribute = (TemplateAttribute) templateAttributeGenService.getById(attrId);
        return new ResponseEntity<>(templateAttribute, HttpStatus.OK);
    }

    /**
     * @param tmpAttrId - id from TemplateAttribute enums
     * @return HttpStatus
     */
    @RequestMapping(value = "/attributes/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTemplateAttributeById(@PathVariable("id") Long tmpAttrId) {
        templateAttributeGenService.delete(tmpAttrId);
        return new ResponseEntity(HttpStatus.OK);
    }


        /*-------------------------------------Template Tasks---------------------------------------*/

    @RequestMapping(value = "/{templId}/tasks/", method = RequestMethod.GET)
    public ResponseEntity<List<TemplateTask>> getTasksByTemplateId(@PathVariable("templId") Long templId) {
        List<TemplateTask> templateTasks = templateTaskService.getTaskByTemplateId(templId);
        return getDefaultResponce(templateTasks,HttpStatus.OK , HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{templId}/tasks/", method = RequestMethod.POST)
    public ResponseEntity<TemplateTask> createTask(
            @PathVariable("templId") Long templId,
            @RequestBody TemplateTask templateTask) {
        templateTask.setTemplateId(templId);
        TemplateTask newTemplateTask = (TemplateTask) templateTaskGenService.save(templateTask);
        return getDefaultResponce(newTemplateTask,HttpStatus.CREATED , HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{templId}/tasks/", method = RequestMethod.PUT)
    public ResponseEntity updateTask(
            @PathVariable("templId") Long templId,
            @RequestBody TemplateTask templateTask) throws NotFoundException {
        templateTask.setTemplateId(templId);
        TemplateTask updated = (TemplateTask) templateTaskGenService.update(templateTask, templateTask.getId());
        return getDefaultResponce(updated,HttpStatus.OK , HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{templId}/tasks/{taskId}", method = RequestMethod.GET)
    public ResponseEntity<TemplateTask> getTemplateTaskById(
            @PathVariable("templId") Long templId,
            @PathVariable("taskId") Long taskId) throws NotFoundException {
        TemplateTask templateTask = (TemplateTask) templateTaskGenService.getById(taskId);
        return new ResponseEntity<>(templateTask, HttpStatus.OK);
    }


    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTemplateTaskById(@PathVariable("id") Long taskId) {
        templateTaskGenService.delete(taskId);
        return new ResponseEntity(HttpStatus.OK);
    }

     /*-------------------------------------Assignment Controls---------------------------------------*/

    /**
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/tasks/{taskId}/controls/", method = RequestMethod.GET)
    public ResponseEntity<List<TemplateTaskControl>> getControlsByTaskId(@PathVariable("taskId") Long taskId) {
        List<TemplateTaskControl> assignmentTaskControlList = templateTaskControlService.getByTaskId(taskId);
        return getDefaultResponce(assignmentTaskControlList,HttpStatus.OK , HttpStatus.NOT_FOUND);
    }

    /**
     * @param taskId
     * @param templateTaskControl
     * @return
     */
    @RequestMapping(value = "/tasks/{taskId}/controls/", method = RequestMethod.POST)
    public ResponseEntity<TemplateTaskControl> createControl(@PathVariable("taskId") int taskId,
                                                             @RequestBody TemplateTaskControl templateTaskControl) {
        TemplateTaskControl newTemplateTaskControl = (TemplateTaskControl) templateTaskControlGeneralService.save(templateTaskControl);
        return getDefaultResponce(newTemplateTaskControl, HttpStatus.CREATED, HttpStatus.NOT_FOUND);
    }

    /**
     * @param taskId
     * @param templateTaskControl
     * @return
     * @throws NotFoundException
     */
    @RequestMapping(value = "/tasks/{taskId}/controls/", method = RequestMethod.PUT)
    public ResponseEntity updateControl(@PathVariable("taskId") int taskId,
                                        @RequestBody TemplateTaskControl templateTaskControl) throws NotFoundException {
        TemplateTaskControl updateTemplateTaskControl =
                (TemplateTaskControl) templateTaskControlGeneralService.update(templateTaskControl, templateTaskControl.getId());
        return getDefaultResponce(updateTemplateTaskControl, HttpStatus.OK, HttpStatus.NOT_FOUND);
    }

    /**
     * @param taskId
     * @param controlId
     * @return
     */
    @RequestMapping(value = "/tasks/{taskId}/controls/{controlId}", method = RequestMethod.GET)
    public ResponseEntity<TemplateTaskControl> getControlById(@PathVariable("taskId") Long taskId,
                                                              @PathVariable("controlId") Long controlId) throws NotFoundException {
        TemplateTaskControl templateTaskControl = (TemplateTaskControl) templateTaskControlGeneralService.getById(controlId);
        return new ResponseEntity<>(templateTaskControl, HttpStatus.OK);
    }

    /**
     * @param controlId
     * @return
     */
    @RequestMapping(value = "/tasks/controls/{controlId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTemplateTaskControlById(@PathVariable("controlId") Long controlId) {
        templateTaskControlGeneralService.delete(controlId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
