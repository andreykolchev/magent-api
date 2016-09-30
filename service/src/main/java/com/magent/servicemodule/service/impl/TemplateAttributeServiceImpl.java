package com.magent.servicemodule.service.impl;

import com.magent.domain.TemplateAttribute;
import com.magent.repository.TemplateAttributeRepository;
import com.magent.servicemodule.service.interfaces.TemplateAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * service for TemplateAttribute operations
 */
@Service
@Transactional(readOnly = true)
class TemplateAttributeServiceImpl implements TemplateAttributeService {

    @Autowired
    private TemplateAttributeRepository templateAttributeRepository;

    /**
     *
     * @param templateId Template id
     * @return list of TemplateAttributes by Template id
     * @see TemplateAttribute
     */
    @Override
    public List<TemplateAttribute> getAttributesByTemplateId(Long templateId) {
        return templateAttributeRepository.findAllByTemplateId(templateId);
    }
}
