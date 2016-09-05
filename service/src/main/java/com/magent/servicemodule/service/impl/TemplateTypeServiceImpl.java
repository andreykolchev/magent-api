package com.magent.servicemodule.service.impl;

import com.magent.domain.TemplateType;
import com.magent.domain.enums.UserRoles;
import com.magent.repository.TemplateTypeRpository;
import com.magent.servicemodule.service.interfaces.TemplateTypeService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Created on 19.07.2016.
 */
@Service
public class TemplateTypeServiceImpl implements TemplateTypeService {
    @Autowired
    private TemplateTypeRpository templateTypeRpository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TemplateType update(TemplateType templateType, Long id) throws NotFoundException {
        templateType.setId(id);
        if (templateType.getUserRolesList().size() == 0 || Objects.isNull(templateType.getUserRolesList()))
            throw new NotFoundException("template type must contain role");
        if (Objects.isNull(templateTypeRpository.findOne(id))) throw new NotFoundException("entity not present in db");
        templateType.setRoles(UserRoles.getRolesSet(templateType.getUserRolesList()));
        TemplateType type = templateTypeRpository.saveAndFlush(templateType);
        type.setUserRolesList(UserRoles.getUserRoles(type.getRoles()));
        return type;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<TemplateType> getChild(Long parentId) {
        return templateTypeRpository.getAllChilds(parentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TemplateType> getTemplateTypesForMobApp(Long role) {
        return templateTypeRpository.getAllowedByRole(role);
    }


}
