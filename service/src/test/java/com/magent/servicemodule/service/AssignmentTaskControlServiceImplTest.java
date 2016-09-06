package com.magent.servicemodule.service;

import com.magent.domain.AssignmentTaskControl;
import com.magent.servicemodule.config.ServiceModuleServiceConfig;
import com.magent.servicemodule.service.interfaces.AssignmentTaskControlService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

/**
 * AssignmentTaskControlServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Sep. 6, 2016</pre>
 */
public class AssignmentTaskControlServiceImplTest extends ServiceModuleServiceConfig {

    @Autowired
    private AssignmentTaskControlService assignmentTaskControlService;

    /**
     * Method: getByTaskId(Number id)
     */
    @Test
    @Sql("classpath:data.sql")
    public void testGetByTaskId() throws Exception {
        Long taskId = 1L;
        List<AssignmentTaskControl> assignmentTaskControlList = assignmentTaskControlService.getByTaskId(taskId);

        assignmentTaskControlList.forEach(assignmentTaskControl -> {
            Assert.assertTrue(assignmentTaskControl instanceof AssignmentTaskControl);
            Assert.assertEquals(taskId,assignmentTaskControl.getAssignmentTaskId());
        });
    }


}
