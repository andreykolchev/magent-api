package com.magent.servicemodule.service.impl;

import com.magent.domain.TemplateTask;
import com.magent.repository.TemplateTaskRepository;
import com.magent.servicemodule.service.interfaces.TemplateTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * service for TemplateTask operations
 */
@Service
@Transactional(readOnly = true)
class TemplateTaskServiceImpl implements TemplateTaskService {

    @Autowired
    private TemplateTaskRepository templateTaskRepository;

    /**
     * @param templateId Template id
     * @return list of TemplateTask by Template id
     * @see com.magent.domain.Template
     */
    @Override
    public List<TemplateTask> getTaskByTemplateId(Long templateId) {
        return templateTaskRepository.getTaskByTemplateId(templateId);
    }
}
