package com.magent.servicemodule.service.impl;

import com.magent.domain.TemplateTaskControl;
import com.magent.repository.TemplateTaskControlRepository;
import com.magent.servicemodule.service.interfaces.TemplateTaskControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class TemplateTaskControlServiceImpl implements TemplateTaskControlService {

    @Autowired
    private TemplateTaskControlRepository templateTaskControlRepository;

    @Override
    public List<TemplateTaskControl> getByTaskId(Number id) {
        return templateTaskControlRepository.getByTemplateTaskId(id);
    }
}
