package com.magent.service;

import com.magent.config.ServiceConfig;
import com.magent.service.interfaces.TemplateTaskControlService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * TemplateTaskControlServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>????. 18, 2016</pre>
 */
public class TemplateTaskControlServiceImplTest extends ServiceConfig {

    @Autowired
    private TemplateTaskControlService templateTaskControlService;
    private Long templateId = 1L;
    private int expectedSize = 2;

    /**
     * Method: getByTaskId(Number id)
     */
    @Test
    public void testGetByTaskId() throws Exception {
        Assert.assertNotNull(templateTaskControlService.getByTaskId(templateId));
        Assert.assertEquals(templateTaskControlService.getByTaskId(templateId).size(), expectedSize);
    }


} 
