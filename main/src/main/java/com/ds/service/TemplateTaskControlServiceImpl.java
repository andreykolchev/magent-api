package com.ds.service;

import com.ds.domain.TemplateTaskControl;
import com.ds.repository.TemplateTaskControlRepository;
import com.ds.service.interfaces.TemplateTaskControlService;
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
