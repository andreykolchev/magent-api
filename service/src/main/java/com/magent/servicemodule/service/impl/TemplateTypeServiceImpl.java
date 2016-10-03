package com.magent.servicemodule.service.impl;

import com.magent.domain.TemplateType;
import com.magent.domain.enums.UserRoles;
import com.magent.repository.TemplateTypeRepository;
import com.magent.servicemodule.service.interfaces.TemplateTypeService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * service for TemplateType operations
 */
@Service
class TemplateTypeServiceImpl implements TemplateTypeService {
    @Autowired
    private TemplateTypeRepository templateTypeRepository;

    /**
     *
     * @param templateType TemplateType object
     * @param id TemplateType id for update
     * @return TemplateType entity persisted in DB
     * @throws NotFoundException if templateTypeRpository.findOne(id) is null
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TemplateType update(TemplateType templateType, Long id) throws NotFoundException {
        templateType.setId(id);
        if (templateType.getUserRolesList().size() == 0 || Objects.isNull(templateType.getUserRolesList()))
            throw new NotFoundException("template type must contain role");
        if (Objects.isNull(templateTypeRepository.findOne(id))) throw new NotFoundException("entity not present in db");
        templateType.setRoles(UserRoles.getRolesSet(templateType.getUserRolesList()));
        TemplateType type = templateTypeRepository.saveAndFlush(templateType);
        type.setUserRolesList(UserRoles.getUserRoles(type.getRoles()));
        return type;
    }

    /**
     * @param parentId id of thr parent TemplateType
     * @return list of child TemplateTypes
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<TemplateType> getChild(Long parentId) {
        return templateTypeRepository.getAllChilds(parentId);
    }

    /**
     * get list of TemplateTypes for current user role
     * @param role current user role
     * @return list of TemplateTypes
     */
    @Override
    @Transactional(readOnly = true)
    public List<TemplateType> getTemplateTypesForMobApp(Long role) {
        return templateTypeRepository.getAllowedByRole(role);
    }


}
