package com.magent.servicemodule.service.interfaces;

import com.magent.domain.TemplateTask;

import java.util.List;

/**
 * Created by artomov.ihor on 10.05.2016.
 */
public interface TemplateTaskService {
    public List<TemplateTask>getTaskByTemplateId(Long templateId);
}
