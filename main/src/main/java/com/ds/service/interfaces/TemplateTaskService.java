package com.ds.service.interfaces;

import com.ds.domain.TemplateTask;

import java.util.List;

/**
 * Created by artomov.ihor on 10.05.2016.
 */
public interface TemplateTaskService {
    public List<TemplateTask>getTaskByTemplateId(Long templateId);
}
