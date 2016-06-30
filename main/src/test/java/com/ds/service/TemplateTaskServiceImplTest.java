package com.ds.service;

import com.ds.config.ServiceConfig;
import com.ds.service.interfaces.TemplateTaskService;
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
public class TemplateTaskServiceImplTest extends ServiceConfig {
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
