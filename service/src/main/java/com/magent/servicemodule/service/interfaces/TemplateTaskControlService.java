package com.magent.servicemodule.service.interfaces;

import com.magent.domain.TemplateTaskControl;

import java.util.List;

public interface TemplateTaskControlService {

    List<TemplateTaskControl> getByTaskId(Number id);

}
