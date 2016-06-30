package com.ds.service;

import com.ds.domain.TemplateTask;
import com.ds.repository.TemplateTaskRepository;
import com.ds.service.interfaces.TemplateTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by artomov.ihor on 10.05.2016.
 */
@Service
@Transactional(readOnly = true)
public class TemplateTaskServiceImpl implements TemplateTaskService {
    @Autowired
    private TemplateTaskRepository templateTaskRepository;
    @Override
    public List<TemplateTask> getTaskByTemplateId(Long templateId) {
        return templateTaskRepository.getTaskByTemplateId(templateId);
    }
}
