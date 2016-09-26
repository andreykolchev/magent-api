package com.magent.servicemodule.service.impl;

import com.magent.domain.TemplateTask;
import com.magent.repository.TemplateTaskRepository;
import com.magent.servicemodule.service.interfaces.TemplateTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by artomov.ihor on 10.05.2016.
 */
@Service
@Transactional(readOnly = true)
class TemplateTaskServiceImpl implements TemplateTaskService {
    @Autowired
    private TemplateTaskRepository templateTaskRepository;
    @Override
    public List<TemplateTask> getTaskByTemplateId(Long templateId) {
        return templateTaskRepository.getTaskByTemplateId(templateId);
    }
}
