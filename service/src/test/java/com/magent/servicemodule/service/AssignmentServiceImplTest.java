package com.magent.servicemodule.service;

import com.magent.domain.Assignment;
import com.magent.repository.AssignmentRepository;
import com.magent.servicemodule.config.ServiceModuleServiceConfig;
import com.magent.servicemodule.service.interfaces.AssignmentService;
import com.magent.servicemodule.utils.EntityGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

/**
 * AssignmentServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.1.1
 * @since <pre>September 5, 2016</pre>
 */
public class AssignmentServiceImplTest extends ServiceModuleServiceConfig {
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private AssignmentRepository assignmentRepository;

    /**
     * Method: createByTemplateId(Assignment assignment)
     */
    @Test
    public void testCreateByTemplateId() throws Exception {
        Assignment assignment=assignmentService.createByTemplateId(EntityGenerator.getTemplateAssignment());
        Assert.assertNotNull(assignment);
    }

    /**
     * Method: assignToUser(Long assignmentId, Long userId)
     */
    @Test
    @Sql("classpath:data.sql")
    public void testAssignToUser() throws Exception {
        //see data.sql test resource and data
        Long expectedUserIdAfterAssign=2L;
        Long userIdBeforeAssign=assignmentRepository.findOne(1L).getUserId();
        Assert.assertNotEquals(userIdBeforeAssign,expectedUserIdAfterAssign);

        assignmentService.assignToUser(assignmentRepository.findOne(1L).getId(), expectedUserIdAfterAssign);
        Long userIdAfterAssign=assignmentRepository.findOne(1L).getUserId();
        //check
        Assert.assertNotEquals("check is it changes",userIdBeforeAssign,userIdAfterAssign);
        Assert.assertEquals("check is it changed on 2 userId",expectedUserIdAfterAssign,userIdAfterAssign);
    }


} 
