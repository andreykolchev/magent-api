package com.ds.service.interfaces;

import com.ds.domain.TemplateAttribute;

import java.util.List;

/**
 * @since 29/04/2016
 */
public interface TemplateAttributeService {
    public List<TemplateAttribute>getAttributesByTemplateId(Long templateId);
}
