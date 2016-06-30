package com.ds.service;

import com.ds.domain.TemplateAttribute;
import com.ds.repository.TemplateAttributeRepository;
import com.ds.service.interfaces.TemplateAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @since 29/04/2016
 */
@Service
@Transactional(readOnly = true)
public class TemplateAttributeServiceImpl implements TemplateAttributeService {
    @Autowired
    private TemplateAttributeRepository templateAttributeRepository;

    @Override
    public List<TemplateAttribute> getAttributesByTemplateId(Long templateId) {
        return templateAttributeRepository.findAllByTemplateId(templateId);
    }
}
