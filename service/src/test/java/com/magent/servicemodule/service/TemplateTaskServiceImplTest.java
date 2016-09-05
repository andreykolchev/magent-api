package com.magent.servicemodule.service;

import com.magent.servicemodule.config.ServiceModuleServiceConfig;
import com.magent.servicemodule.service.interfaces.TemplateTaskService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * TemplateTaskServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>????. 18, 2016</pre>
 */
public class TemplateTaskServiceImplTest extends ServiceModuleServiceConfig {
    @Autowired
    private TemplateTaskService templateTaskService;
    private Long templateId=1L;
    private int expectedSize=3;
    /**
     * Method: getTaskByTemplateId(Long templateId)
     */
    @Test
    public void testGetTaskByTemplateId() throws Exception {
        Assert.assertNotNull(templateTaskService.getTaskByTemplateId(templateId));
        Assert.assertEquals(templateTaskService.getTaskByTemplateId(templateId).size(),expectedSize);
    }


} 
