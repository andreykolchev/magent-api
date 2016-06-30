package com.ds.service;

import com.ds.config.ServiceConfig;
import com.ds.domain.Assignment;
import com.ds.domain.AssignmentAttribute;
import com.ds.domain.User;
import com.ds.domain.dto.UpdateDataDto;
import com.ds.service.interfaces.AssignmentAttributeService;
import com.ds.service.interfaces.DataService;
import com.ds.service.interfaces.GeneralService;
import com.ds.utils.AssignmentAttributesGenerator;
import com.ds.utils.CommissionUtils;
import com.ds.utils.EntityGenerator;
import com.ds.utils.ariphmeticbeans.ComissionCalculatorImpl;
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
public class DataServiceImplTest extends ServiceConfig {
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
        User user= (User) userGeneral.getById(assignment.getUserId());
        Assert.assertEquals(expectedBalance,user.getAccount().getAccountBalance());
    }


}
