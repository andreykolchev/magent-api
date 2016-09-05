package com.magent.utilbeanstest;

import com.magent.config.ServiceConfig;
import com.magent.utils.AssignmentAttributesGenerator;
import com.magent.servicemodule.utils.ariphmeticbeans.ComissionCalculator;
import com.magent.servicemodule.utils.ariphmeticbeans.ComissionCalculatorImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.SpelEvaluationException;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by artomov.ihor on 25.05.2016.
 */
public class ComissionCalculatorImplTest extends ServiceConfig {
    @Autowired
    ComissionCalculator comissionService;

    @Test
    public void calculateCommissionTest() throws ComissionCalculatorImpl.FormulaNotFound {
        Number expectedResult = 0.4;
        Assert.assertEquals(expectedResult, comissionService.calculateCommission(AssignmentAttributesGenerator.getTestDataExpextedResult1(),1));
    }

    @Test
    public void calculateCommissionTest2() throws ComissionCalculatorImpl.FormulaNotFound {
        Number expectedResult = 340.05;
        Assert.assertEquals(expectedResult, comissionService.calculateCommission(AssignmentAttributesGenerator.getTestDataExcpected2(),2));
    }

    @Test
    public void calculateCommissionTest5() throws ComissionCalculatorImpl.FormulaNotFound {
        Double expectedResult = new BigDecimal(57.7525).setScale(3, RoundingMode.DOWN).doubleValue();
        Assert.assertEquals(expectedResult, comissionService.calculateCommission(AssignmentAttributesGenerator.getTestDataExcpected5(),3));
    }

    @Test
    public void calculateCommissionWithdDublicateTest6() throws ComissionCalculatorImpl.FormulaNotFound {
        Number expectedResult = 25.9886;//true res
        //round
        Assert.assertEquals(expectedResult,  comissionService.calculateCommission(AssignmentAttributesGenerator.getTestDataExcpected6(),4));
    }


    @Test(expected = SpelEvaluationException.class)
    public void calculateCommisionTest3Negative() throws ComissionCalculatorImpl.FormulaNotFound {
        Number expectedResult = 340.05;
        Assert.assertEquals(expectedResult, comissionService.calculateCommission(AssignmentAttributesGenerator.getTestData3(),2));
    }

    @Test(expected = ComissionCalculatorImpl.FormulaNotFound.class)
    public void calculateCommisionTest4Negative() throws ComissionCalculatorImpl.FormulaNotFound {
        Number expectedResult = 340.05;
        Assert.assertEquals(expectedResult, comissionService.calculateCommission(AssignmentAttributesGenerator.getTestDataExcpected4(),2));
    }


}
