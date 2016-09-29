package com.magent.controller;

import com.magent.controller.interfaces.GeneralController;
import com.magent.domain.Template;
import com.magent.domain.TemplateAttribute;
import com.magent.domain.TemplateTask;
import com.magent.domain.TemplateTaskControl;
import com.magent.servicemodule.service.interfaces.GeneralService;
import com.magent.servicemodule.service.interfaces.TemplateAttributeService;
import com.magent.servicemodule.service.interfaces.TemplateTaskControlService;
import com.magent.servicemodule.service.interfaces.TemplateTaskService;
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
    private GeneralService<Template> templateGeneralService;

    @Autowired
    @Qualifier("templateAttributeGeneralService")
    private GeneralService<TemplateAttribute> templateAttributeGenService;

    @Autowired
    @Qualifier("templateAttributeServiceImpl")
    private TemplateAttributeService templateAttributeService;

    @Autowired
    @Qualifier("templateTaskGeneralService")
    private GeneralService<TemplateTask> templateTaskGenService;

    @Autowired
    @Qualifier("templateTaskServiceImpl")
    private TemplateTaskService templateTaskService;

    @Autowired
    @Qualifier("templateTaskControlGeneralService")
    private GeneralService<TemplateTaskControl> templateTaskControlGeneralService;

    @Autowired
    @Qualifier("templateTaskControlServiceImpl")
    private TemplateTaskControlService templateTaskControlService;

    /**
     * @return all list of Template
     * @see Template class entity
     */

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Template> get() throws NotFoundException {
        return templateGeneralService.getAll();
    }

    /**
     * @param template Template entity without id
     * @return created Template entity
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Template> create(@RequestBody Template template) {
        Template newTemplate = templateGeneralService.save(template);
        return getDefaultResponce(newTemplate, HttpStatus.CREATED, HttpStatus.NOT_FOUND);
    }

    /**
     * @param template Template entity with id
     * @return updated Template entity
     * @throws NotFoundException if id not present or entity not present in db
     */
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<Template> update(@RequestBody Template template) throws NotFoundException {
        Template updatedTemplate = templateGeneralService.update(template, template.getId());
        return getDefaultResponce(updatedTemplate, HttpStatus.OK, HttpStatus.NOT_FOUND);

    }

    /**
     * @param id of enums
     * @return Template enums which has this id
     * @throws NotFoundException if id not present or entity not present in db
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Template> getTemplateById(@PathVariable("id") Long id) throws NotFoundException {
        Template template = templateGeneralService.getById(id);
        return new ResponseEntity<>(template, HttpStatus.OK);
    }

    /**
     * Important information template can be deleted if it don't have any assignments
     *
     * @param id entity id
     * @return HttpStatus.OK if service won't throw any exception
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTemplateById(@PathVariable("id") Long id) {
        templateGeneralService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /*-------------------------------------Template attributes---------------------------------------*/

    /**
     * @param templId id from Template class
     * @return TemplateAttribute entity which has current template.id
     */
    @RequestMapping(value = "/{templId}/attributes/", method = RequestMethod.GET)
    public ResponseEntity<List<TemplateAttribute>> getAttributesByTemplateId(
            @PathVariable("templId") Long templId) {
        List<TemplateAttribute> templateAttributes = templateAttributeService.getAttributesByTemplateId(templId);
        return getDefaultResponce(templateAttributes, HttpStatus.OK, HttpStatus.NOT_FOUND);
    }

    /**
     * @param templId           id from Template entity
     * @param templateAttribute TemplateAttribute entity which will be created without id
     * @return created TemplateAttribute
     */
    @RequestMapping(value = "/{templId}/attributes/", method = RequestMethod.POST)
    public ResponseEntity<TemplateAttribute> createAttribute(
            @PathVariable("templId") Long templId,
            @RequestBody TemplateAttribute templateAttribute) {
        templateAttribute.setTemplateId(templId);
        TemplateAttribute newTemplateAttribute = templateAttributeGenService.save(templateAttribute);
        return getDefaultResponce(newTemplateAttribute, HttpStatus.CREATED, HttpStatus.NOT_FOUND);
    }

    /**
     * @param templId           id from Template entity
     * @param templateAttribute TemplateAttribute enums which will be updated with id
     * @return updated TemplateAttribute entity
     * @throws NotFoundException
     */
    @RequestMapping(value = "/{templId}/attributes/", method = RequestMethod.PUT)
    public ResponseEntity updateAttribute(
            @PathVariable("templId") Long templId,
            @RequestBody TemplateAttribute templateAttribute) throws NotFoundException {
        templateAttribute.setTemplateId(templId);
        TemplateAttribute updated = templateAttributeGenService.update
                (templateAttribute, templateAttribute.getId());
        return getDefaultResponce(updated, HttpStatus.OK, HttpStatus.NOT_FOUND);

    }

    /**
     * @param templId id from Template entity
     * @param attrId  id from TemplateAttribute entity
     * @return TemplateAttribute with current attrId
     */
    @RequestMapping(value = "/{templId}/attributes/{attrId}", method = RequestMethod.GET)
    public ResponseEntity<TemplateAttribute> getAttributeById(
            @PathVariable("templId") Long templId,
            @PathVariable("attrId") Long attrId) throws NotFoundException {
        TemplateAttribute templateAttribute = templateAttributeGenService.getById(attrId);
        return new ResponseEntity<>(templateAttribute, HttpStatus.OK);
    }

    /**
     * @param tmpAttrId -id from TemplateAttribute entity
     * @return HttpStatus.OK if service won't throw any exception
     */
    @RequestMapping(value = "/attributes/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTemplateAttributeById(@PathVariable("id") Long tmpAttrId) {
        templateAttributeGenService.delete(tmpAttrId);
        return new ResponseEntity(HttpStatus.OK);
    }


        /*-------------------------------------Template Tasks---------------------------------------*/

    /**
     * @param templId Template id
     * @return TemplateTask entity with templateId variable
     */

    @RequestMapping(value = "/{templId}/tasks/", method = RequestMethod.GET)
    public ResponseEntity<List<TemplateTask>> getTasksByTemplateId(@PathVariable("templId") Long templId) {
        List<TemplateTask> templateTasks = templateTaskService.getTaskByTemplateId(templId);
        return getDefaultResponce(templateTasks, HttpStatus.OK, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{templId}/tasks/", method = RequestMethod.POST)
    public ResponseEntity<TemplateTask> createTask(
            @PathVariable("templId") Long templId,
            @RequestBody TemplateTask templateTask) {
        templateTask.setTemplateId(templId);
        TemplateTask newTemplateTask = templateTaskGenService.save(templateTask);
        return getDefaultResponce(newTemplateTask, HttpStatus.CREATED, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{templId}/tasks/", method = RequestMethod.PUT)
    public ResponseEntity updateTask(
            @PathVariable("templId") Long templId,
            @RequestBody TemplateTask templateTask) throws NotFoundException {
        templateTask.setTemplateId(templId);
        TemplateTask updated = templateTaskGenService.update(templateTask, templateTask.getId());
        return getDefaultResponce(updated, HttpStatus.OK, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{templId}/tasks/{taskId}", method = RequestMethod.GET)
    public ResponseEntity<TemplateTask> getTemplateTaskById(
            @PathVariable("templId") Long templId,
            @PathVariable("taskId") Long taskId) throws NotFoundException {
        TemplateTask templateTask = templateTaskGenService.getById(taskId);
        return new ResponseEntity<>(templateTask, HttpStatus.OK);
    }


    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTemplateTaskById(@PathVariable("id") Long taskId) {
        templateTaskGenService.delete(taskId);
        return new ResponseEntity(HttpStatus.OK);
    }

     /*-------------------------------------Assignment Controls---------------------------------------*/

    /**
     * @param taskId id of TemplateTask entity
     * @return list TemplateTaskControl entity which have taskId variable
     */
    @RequestMapping(value = "/tasks/{taskId}/controls/", method = RequestMethod.GET)
    public ResponseEntity<List<TemplateTaskControl>> getControlsByTaskId(@PathVariable("taskId") Long taskId) {
        List<TemplateTaskControl> assignmentTaskControlList = templateTaskControlService.getByTaskId(taskId);
        return getDefaultResponce(assignmentTaskControlList, HttpStatus.OK, HttpStatus.NOT_FOUND);
    }

    /**
     * @param taskId              id of TemplateTask entity
     * @param templateTaskControl TemplateTaskControl entity
     * @return created TemplateTaskControl entity
     */
    @RequestMapping(value = "/tasks/{taskId}/controls/", method = RequestMethod.POST)
    public ResponseEntity<TemplateTaskControl> createControl(@PathVariable("taskId") int taskId,
                                                             @RequestBody TemplateTaskControl templateTaskControl) {
        TemplateTaskControl newTemplateTaskControl = templateTaskControlGeneralService.save(templateTaskControl);
        return getDefaultResponce(newTemplateTaskControl, HttpStatus.CREATED, HttpStatus.NOT_FOUND);
    }

    /**
     * @return  updated TemplateTaskControl
     * @throws NotFoundException if current entity not present id db
     */
    @RequestMapping(value = "/tasks/{taskId}/controls/", method = RequestMethod.PUT)
    public ResponseEntity<TemplateTaskControl> updateControl(@PathVariable("taskId") int taskId,
                                        @RequestBody TemplateTaskControl templateTaskControl) throws NotFoundException {
        TemplateTaskControl updateTemplateTaskControl =
                templateTaskControlGeneralService.update(templateTaskControl, templateTaskControl.getId());
        return getDefaultResponce(updateTemplateTaskControl, HttpStatus.OK, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/tasks/{taskId}/controls/{controlId}", method = RequestMethod.GET)
    public ResponseEntity<TemplateTaskControl> getControlById(@PathVariable("taskId") Long taskId,
                                                              @PathVariable("controlId") Long controlId) throws NotFoundException {
        TemplateTaskControl templateTaskControl = templateTaskControlGeneralService.getById(controlId);
        return new ResponseEntity<>(templateTaskControl, HttpStatus.OK);
    }

    /**
     * @return HttpStatus.OK if entity was deleted
     */
    @RequestMapping(value = "/tasks/controls/{controlId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTemplateTaskControlById(@PathVariable("controlId") Long controlId) {
        templateTaskControlGeneralService.delete(controlId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
