package com.magent.servicemodule.service;

import com.magent.domain.AssignmentTask;
import com.magent.servicemodule.config.ServiceModuleServiceConfig;
import com.magent.servicemodule.service.interfaces.AssignmentTaskService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

/**
 * AssignmentTaskServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Sep. 6, 2016</pre>
 */
public class AssignmentTaskServiceImplTest extends ServiceModuleServiceConfig {
    @Autowired
    private AssignmentTaskService assignmentTaskService;

    /**
     * Method: getByAssignmentId(Number id)
     */
    @Test
    @Sql("classpath:data.sql")
    public void testGetByAssignmentId() throws Exception {
        Long assignmentId=1L;
        List<AssignmentTask>assignmentTaskList=assignmentTaskService.getByAssignmentId(assignmentId);
        Assert.assertNotNull(assignmentTaskList);
        Assert.assertTrue(assignmentTaskList.size()>0);
        assignmentTaskList.forEach(assignmentTask -> {
            Assert.assertEquals("check for current working",assignmentId,assignmentTask.getAssignmentId());
        });
    }


} 
