package com.magent.service.interfaces;

import com.magent.domain.TemplateType;
import javassist.NotFoundException;
import javassist.tools.web.BadHttpRequest;

import java.util.List;

/**
 * Created on 19.07.2016.
 */
public interface TemplateTypeService {
    TemplateType update(TemplateType templateType,Long id) throws BadHttpRequest, NotFoundException;
    List<TemplateType>getChild(Long parentId);
    List<TemplateType>getTemplateTypesForMobApp(Long role);
}
