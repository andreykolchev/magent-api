package com.ds.service;

import com.ds.config.ServiceConfig;
import com.ds.domain.TemplateAttribute;
import com.ds.service.interfaces.TemplateAttributeService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * TemplateAttributeServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>????. 18, 2016</pre>
 */
public class TemplateAttributeServiceImplTest extends ServiceConfig {

    @Autowired
    private TemplateAttributeService attributeService;

    private Long templateId = 1L;
    private int excpectedSizeOfCollection = 4;

    /**
     * Method: getAttributesByTemplateId(Long templateId)
     */
    @Test
    public void testGetAttributesByTemplateId() throws Exception {
        Assert.assertNotNull(attributeService.getAttributesByTemplateId(templateId));
        Assert.assertEquals("checking size of collection", attributeService.getAttributesByTemplateId(templateId).size(), excpectedSizeOfCollection);
        Assert.assertTrue(attributeService.getAttributesByTemplateId(templateId).get(0) instanceof TemplateAttribute);
    }


} 
