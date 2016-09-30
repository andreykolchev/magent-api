package com.magent.servicemodule.service.impl;

import com.magent.domain.TemplateTaskControl;
import com.magent.repository.TemplateTaskControlRepository;
import com.magent.servicemodule.service.interfaces.TemplateTaskControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * service for TemplateTaskControl operations
 */
@Service
@Transactional(readOnly = true)
class TemplateTaskControlServiceImpl implements TemplateTaskControlService {

    @Autowired
    private TemplateTaskControlRepository templateTaskControlRepository;

    /**
     *
     * @param id TemplateTask id
     * @return list of TemplateTaskControl by TemplateTask id
     * @see com.magent.domain.TemplateTask
     */
    @Override
    public List<TemplateTaskControl> getByTaskId(Number id) {
        return templateTaskControlRepository.getByTemplateTaskId(id);
    }
}
