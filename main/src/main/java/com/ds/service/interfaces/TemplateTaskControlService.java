package com.ds.service.interfaces;

import com.ds.domain.TemplateTaskControl;

import java.util.List;

public interface TemplateTaskControlService {

    List<TemplateTaskControl> getByTaskId(Number id);

}
