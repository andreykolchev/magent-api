package com.magent.controller;

import com.magent.controller.interfaces.GeneralController;
import com.magent.domain.TemplateType;
import com.magent.servicemodule.service.interfaces.GeneralService;
import com.magent.servicemodule.service.interfaces.TemplateTypeService;
import javassist.NotFoundException;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created on 19.07.2016.
 */
@RestController
@RequestMapping(value = "/template-types")
public class TemplateTypeControllerImpl implements GeneralController {
    @Autowired
    @Qualifier("templateTypeGeneralService")
    private GeneralService templateTypeGeneral;

    @Autowired
    private TemplateTypeService templateTypeService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<TemplateType>> getAll() throws NotFoundException {
        return getDefaultResponce(templateTypeGeneral.getAll(), HttpStatus.OK, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<TemplateType> getById(@PathVariable Long id) throws NotFoundException {
        return getDefaultResponce(templateTypeGeneral.getById(id), HttpStatus.OK, HttpStatus.NOT_FOUND);
    }
    @RequestMapping(value = "/{id}/childs",method = RequestMethod.GET)
    public ResponseEntity<List<TemplateType>>getChilds(@PathVariable Long id){
        return getDefaultResponce(templateTypeService.getChild(id),HttpStatus.OK,HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<TemplateType> createTemplateType(@RequestBody TemplateType templateType) throws NotFoundException {
        return getDefaultResponce((TemplateType) templateTypeGeneral.save(templateType), HttpStatus.CREATED, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<TemplateType> updateTemplateType(@PathVariable Long id, @RequestBody TemplateType templateType) throws NotFoundException, BadHttpRequest {
        return getDefaultResponce( templateTypeService.update(templateType, id), HttpStatus.OK, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable Long id) {
        templateTypeGeneral.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
