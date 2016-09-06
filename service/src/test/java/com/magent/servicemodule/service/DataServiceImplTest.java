package com.magent.servicemodule.service;

import com.magent.domain.Assignment;
import com.magent.domain.AssignmentAttribute;
import com.magent.domain.AssignmentStatus;
import com.magent.domain.User;
import com.magent.domain.dto.UpdateDataDto;
import com.magent.servicemodule.config.ServiceModuleServiceConfig;
import com.magent.servicemodule.service.interfaces.AssignmentAttributeService;
import com.magent.servicemodule.service.interfaces.DataService;
import com.magent.servicemodule.service.interfaces.GeneralService;
import com.magent.servicemodule.utils.AssignmentAttributesGenerator;
import com.magent.servicemodule.utils.CommissionUtils;
import com.magent.servicemodule.utils.EntityGenerator;
import com.magent.servicemodule.utils.ariphmeticbeans.ComissionCalculatorImpl;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.test.context.jdbc.Sql;

import java.text.ParseException;
import java.util.List;

/**
 * Created by artomov.ihor on 30.05.2016.
 */
public class DataServiceImplTest extends ServiceModuleServiceConfig {
    @Autowired
    private DataService dataService;

    @Autowired
    @Qualifier("assignmentAttributeGeneralService")
    private GeneralService assignmentAttrGenService;

    @Autowired
    @Qualifier("userGeneralService")
    private GeneralService userGeneral;

    @Autowired
    private AssignmentAttributeService attributeService;

    @Test
    @Sql("classpath:data.sql")
    public void updateTestWithCommissionCalculator() throws ComissionCalculatorImpl.FormulaNotFound, ChangeSetPersister.NotFoundException, NotFoundException, ParseException {
        //attributes for calculating formula
        //expected commission summ 25.98
        Double expected = 25.98;
        List<AssignmentAttribute> attributeList = AssignmentAttributesGenerator.getTestDataExcpected6();
        assignmentAttrGenService.saveAll(attributeList);
        UpdateDataDto dataDto = EntityGenerator.getUpdateDataDto();
        //test
        dataService.updateData(dataDto);
        Double cost = CommissionUtils.getCommissionCost(attributeService.getByAssignmentId(1L));
        Assert.assertEquals(expected, cost);

    }

    @Test
    @Sql("classpath:data.sql")
    public void updateTestWithTransaction() throws ComissionCalculatorImpl.FormulaNotFound, ParseException, NotFoundException, ChangeSetPersister.NotFoundException {
        //attributes for calculating formula
        //expected commission summ 25.98
        //expected accountBalance 1400.09+25.98=1426.07
        Double expectedSummAdd = 25.98;
        Double expectedBalance = 1426.07;

        List<AssignmentAttribute> attributeList = AssignmentAttributesGenerator.getTestDataExcpected6();
        assignmentAttrGenService.saveAll(attributeList);
        UpdateDataDto dataDto = EntityGenerator.getUpdateDataDtoTransaction();
        Assignment assignment = dataDto.getAssignments().get(0);
        //test
        dataService.updateData(dataDto);
        Double cost = CommissionUtils.getCommissionCost(attributeService.getByAssignmentId(1L));
        Assert.assertEquals(expectedSummAdd, cost);
        User user = (User) userGeneral.getById(assignment.getUserId());
        Assert.assertEquals(expectedBalance, user.getAccount().getAccountBalance());
    }

    @Test
    @Sql("classpath:data.sql")
    public void testFullRegistartionUpdateData() throws ComissionCalculatorImpl.FormulaNotFound, ParseException, NotFoundException {
        UpdateDataDto dataDto = EntityGenerator.getUpdateDataDtoForFullRegistrationFull();
        dataDto = dataService.updateData(dataDto);
        Assert.assertEquals("check for status should be NEED_CONFIRMATION", AssignmentStatus.NEED_CONFIRMATION, dataDto.getAssignments().get(0).getStatus());
    }

    @Test
    @Sql("classpath:data.sql")
    public void getDataTest() {
        Long userId = 1L;
        UpdateDataDto updateDataDto = dataService.getData(userId, null);
        //test
        Assert.assertNotNull("check is syncId added", updateDataDto.getSyncId());
        updateDataDto.getAssignments().forEach(assignment -> {
            Assert.assertEquals("check is it assignments for current user", userId, assignment.getUserId());
            Assert.assertNotNull("check for Template Type Description ", assignment.getTemplateTypeDescription());
            Assert.assertNotNull("check is attributes fetched", assignment.getAttributes());

            assignment.getAttributes().forEach(assignmentAttribute -> {
                Assert.assertEquals(assignment.getId(), assignmentAttribute.getAssignmentId());
            });

            assignment.getTasks().forEach(assignmentTask -> {
                assignmentTask.getControls().forEach(assignmentTaskControl -> {
                    Assert.assertEquals("check is controls inicializing", assignmentTask.getId(), assignmentTaskControl.getAssignmentTaskId());
                });
            });

        });
    }

}
