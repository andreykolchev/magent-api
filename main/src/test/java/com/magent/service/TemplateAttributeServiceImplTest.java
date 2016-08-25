package com.magent.service;

import com.magent.config.ServiceConfig;
import com.magent.domain.TemplateAttribute;
import com.magent.service.interfaces.TemplateAttributeService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

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
    @Sql("classpath:data.sql")
    public void testGetAttributesByTemplateId() throws Exception {
        Assert.assertNotNull(attributeService.getAttributesByTemplateId(templateId));
        Assert.assertEquals("checking size of collection", attributeService.getAttributesByTemplateId(templateId).size(), excpectedSizeOfCollection);
        Assert.assertTrue(attributeService.getAttributesByTemplateId(templateId).get(0) instanceof TemplateAttribute);
    }


} 
