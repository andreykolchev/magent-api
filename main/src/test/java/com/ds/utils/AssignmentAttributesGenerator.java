package com.ds.utils;

import com.ds.domain.AssignmentAttribute;
import com.ds.domain.ValueType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by artomov.ihor on 26.05.2016.
 */
public class AssignmentAttributesGenerator {

    public static List<AssignmentAttribute> getTestDataExpextedResult1() {
        //expected result 0.4
        AssignmentAttribute costAttr = new AssignmentAttribute("COST", "COST", ValueType.NUMERIC, String.valueOf(20), true, false, true, LocalDate.now().toEpochDay(), 1L);
        AssignmentAttribute percentAttr = new AssignmentAttribute("PERCENT", "PERCENT", ValueType.NUMERIC, String.valueOf(0.1), true, false, true, LocalDate.now().toEpochDay(), 1l);
        AssignmentAttribute formulaAttr = new AssignmentAttribute("FORMULA", "FORMULA", ValueType.FORMULA, "(COST*PERCENT)/5", false, false, true, LocalDate.now().toEpochDay(), 1L);
        return new ArrayList<>(Arrays.asList(costAttr, percentAttr, formulaAttr));
    }

    public static List<AssignmentAttribute> getTestDataExcpected2() {
        //expected 340.05
        AssignmentAttribute costAttr = new AssignmentAttribute("COST", "COST", ValueType.NUMERIC, String.valueOf(2000), true, false, true, LocalDate.now().toEpochDay(), 1L);
        AssignmentAttribute percentAttr = new AssignmentAttribute("PERCENT", "PERCENT", ValueType.NUMERIC, String.valueOf(0.1), true, false, true, LocalDate.now().toEpochDay(), 1L);
        AssignmentAttribute percentAttr1 = new AssignmentAttribute("CREDITLIMITSIZE", "CREDITLIMITSIZE", ValueType.NUMERIC, String.valueOf(500), true, false, true, LocalDate.now().toEpochDay(), 1L);
        AssignmentAttribute percentAttr2 = new AssignmentAttribute("CREDITPERCENT", "CREDITPERCENT", ValueType.NUMERIC, String.valueOf(0.05), true, false, true, LocalDate.now().toEpochDay(), 1L);
        AssignmentAttribute percentAttr3 = new AssignmentAttribute("FIXEDSUMM", "FIXEDSUMM", ValueType.NUMERIC, String.valueOf(115.05), true, false, true, LocalDate.now().toEpochDay(), 1L);
        AssignmentAttribute formulaAttr = new AssignmentAttribute("FORMULA", "FORMULA", ValueType.FORMULA, "(COST*PERCENT)+(CREDITLIMITSIZE*CREDITPERCENT)+(FIXEDSUMM)", false, false, true, LocalDate.now().toEpochDay(), 1L);
        return new ArrayList<>(Arrays.asList(costAttr, percentAttr, percentAttr1, percentAttr2, percentAttr3, formulaAttr));
    }

    public static List<AssignmentAttribute> getTestData3() {
        AssignmentAttribute formulaAttr = new AssignmentAttribute("FORMULA", "FORMULA", ValueType.FORMULA, "(COST*PERCENT)+(CREDITLIMITSIZE*CREDITPERCENT)+(FIXEDSUMM)", false, false, true, LocalDate.now().toEpochDay(), 1L);
        AssignmentAttribute costAttr = new AssignmentAttribute("COST", "COST", ValueType.NUMERIC, String.valueOf(2000), true, false, true, LocalDate.now().toEpochDay(), 1L);
        AssignmentAttribute percentAttr = new AssignmentAttribute("PERCENT", "PERCENT", ValueType.NUMERIC, String.valueOf(0.1), true, false, true, LocalDate.now().toEpochDay(), 1l);
        AssignmentAttribute percentAttr1 = new AssignmentAttribute("CREDITLIMITSIZE", "CREDITLIMITSIZE", ValueType.NUMERIC, String.valueOf(500), true, false, true, LocalDate.now().toEpochDay(), 1l);
        AssignmentAttribute percentAttr2 = new AssignmentAttribute("CREDITPERCENT", "CREDITPERCENT", ValueType.NUMERIC, String.valueOf(0.05), true, false, true, LocalDate.now().toEpochDay(), 1l);
        return new ArrayList<>(Arrays.asList(costAttr, percentAttr, percentAttr1, percentAttr2, formulaAttr));
    }

    public static List<AssignmentAttribute> getTestDataExcpected4() {
        //expected 340.05
        AssignmentAttribute costAttr = new AssignmentAttribute("COST", "COST", ValueType.NUMERIC, String.valueOf(2000), true, false, true, LocalDate.now().toEpochDay(), 1L);
        AssignmentAttribute percentAttr = new AssignmentAttribute("PERCENT", "PERCENT", ValueType.NUMERIC, String.valueOf(0.1), true, false, true, LocalDate.now().toEpochDay(), 1l);
        AssignmentAttribute percentAttr1 = new AssignmentAttribute("CREDITLIMITSIZE", "CREDITLIMITSIZE", ValueType.NUMERIC, String.valueOf(500), true, false, true, LocalDate.now().toEpochDay(), 1l);
        AssignmentAttribute percentAttr2 = new AssignmentAttribute("CREDITPERCENT", "CREDITPERCENT", ValueType.NUMERIC, String.valueOf(0.05), true, false, true, LocalDate.now().toEpochDay(), 1l);
        AssignmentAttribute percentAttr3 = new AssignmentAttribute("FIXEDSUMM", "FIXEDSUMM", ValueType.NUMERIC, String.valueOf(115.05), true, false, true, LocalDate.now().toEpochDay(), 1l);
        return new ArrayList<>(Arrays.asList(costAttr, percentAttr, percentAttr1, percentAttr2, percentAttr3));
    }

    public static List<AssignmentAttribute> getTestDataExcpected5() {
        //expected 57.7525
            AssignmentAttribute costAttr = new AssignmentAttribute("COST", "COST", ValueType.NUMERIC, String.valueOf(20300), true, false, true, LocalDate.now().toEpochDay(), 1L);
        AssignmentAttribute percentAttr = new AssignmentAttribute("PERCENT", "PERCENT", ValueType.NUMERIC, String.valueOf(0.05), true, false, true, LocalDate.now().toEpochDay(), 1l);
        AssignmentAttribute percentAttr1 = new AssignmentAttribute("CREDITLIMITSIZE", "CREDITLIMITSIZE", ValueType.NUMERIC, String.valueOf(500), true, false, true, LocalDate.now().toEpochDay(), 1l);
        AssignmentAttribute percentAttr2 = new AssignmentAttribute("CREDITPERCENT", "CREDITPERCENT", ValueType.NUMERIC, String.valueOf(0.05), true, false, true, LocalDate.now().toEpochDay(), 1l);
        AssignmentAttribute percentAttr3 = new AssignmentAttribute("FIXEDSUMM", "FIXEDSUMM", ValueType.NUMERIC, String.valueOf(115.05), true, false, true, LocalDate.now().toEpochDay(), 1l);
        AssignmentAttribute percentAttr4 = new AssignmentAttribute("PERCENTOFDEBITSUMM", "PERCENTOFDEBITSUMM", ValueType.NUMERIC, String.valueOf(0.15), true, false, true, LocalDate.now().toEpochDay(), 1l);
        AssignmentAttribute percentAttr5 = new AssignmentAttribute("SUMMOFSOMETHING", "SUMMOFSOMETHING", ValueType.NUMERIC, String.valueOf(3), true, false, true, LocalDate.now().toEpochDay(), 1l);
        AssignmentAttribute formulaAttr = new AssignmentAttribute("FORMULA", "FORMULA", ValueType.FORMULA, "(((COST*PERCENT)+(CREDITLIMITSIZE*CREDITPERCENT)+(FIXEDSUMM))/SUMMOFSOMETHING)*PERCENTOFDEBITSUMM", false, false, true, LocalDate.now().toEpochDay(), 1L);
         return new ArrayList<>(Arrays.asList(costAttr, percentAttr, percentAttr1, percentAttr2, percentAttr3, formulaAttr,percentAttr4,percentAttr5));
    }

    public static List<AssignmentAttribute> getTestDataExcpected6() {
        //expected 25.9886
        AssignmentAttribute costAttr = new AssignmentAttribute("COST", "COST", ValueType.NUMERIC, String.valueOf(20300), true, false, true, LocalDate.now().toEpochDay(), 1L);
        AssignmentAttribute percentAttr = new AssignmentAttribute("PERCENT", "PERCENT", ValueType.NUMERIC, String.valueOf(0.05), true, false, true, LocalDate.now().toEpochDay(), 1l);
        AssignmentAttribute percentAttr1 = new AssignmentAttribute("CREDITLIMITSIZE", "CREDITLIMITSIZE", ValueType.NUMERIC, String.valueOf(500), true, false, true, LocalDate.now().toEpochDay(), 1l);
        AssignmentAttribute percentAttr2 = new AssignmentAttribute("CREDITPERCENT", "CREDITPERCENT", ValueType.NUMERIC, String.valueOf(0.05), true, false, true, LocalDate.now().toEpochDay(), 1l);
        AssignmentAttribute percentAttr3 = new AssignmentAttribute("FIXEDSUMM", "FIXEDSUMM", ValueType.NUMERIC, String.valueOf(115.05), true, false, true, LocalDate.now().toEpochDay(), 1l);
        AssignmentAttribute percentAttr4 = new AssignmentAttribute("PERCENTOFDEBITSUMM", "PERCENTOFDEBITSUMM", ValueType.NUMERIC, String.valueOf(0.15), true, false, true, LocalDate.now().toEpochDay(), 1l);
        AssignmentAttribute percentAttr5 = new AssignmentAttribute("SUMMOFSOMETHING", "SUMMOFSOMETHING", ValueType.NUMERIC, String.valueOf(3), true, false, true, LocalDate.now().toEpochDay(), 1l);
        AssignmentAttribute formulaAttr = new AssignmentAttribute("FORMULA", "FORMULA", ValueType.FORMULA, "((((COST*PERCENT)+(CREDITLIMITSIZE*CREDITPERCENT)+(FIXEDSUMM))/SUMMOFSOMETHING)*PERCENTOFDEBITSUMM)*(SUMMOFSOMETHING*PERCENTOFDEBITSUMM)", false, false, true, LocalDate.now().toEpochDay(), 1L);
        AssignmentAttribute commissionAttr = new AssignmentAttribute("COMMISSION", "COMMISSION", ValueType.COMMISSION_COST, "", false, false, true, LocalDate.now().toEpochDay(), 1L);
        return new ArrayList<>(Arrays.asList(costAttr, percentAttr, percentAttr1, percentAttr2, percentAttr3, formulaAttr,percentAttr4,percentAttr5, commissionAttr));
    }

    public static List<AssignmentAttribute> getTestDataExcpectedForUpdateData() {
        //expected 340.05
        AssignmentAttribute costAttr = new AssignmentAttribute("COST", "COST", ValueType.NUMERIC, String.valueOf(2000), true, false, true, LocalDate.now().toEpochDay(), 1L);
        AssignmentAttribute percentAttr = new AssignmentAttribute("PERCENT", "PERCENT", ValueType.NUMERIC, String.valueOf(0.1), true, false, true, LocalDate.now().toEpochDay(), 1L);
        AssignmentAttribute percentAttr1 = new AssignmentAttribute("CREDITLIMITSIZE", "CREDITLIMITSIZE", ValueType.NUMERIC, String.valueOf(500), true, false, true, LocalDate.now().toEpochDay(), 1L);
        AssignmentAttribute percentAttr2 = new AssignmentAttribute("CREDITPERCENT", "CREDITPERCENT", ValueType.NUMERIC, String.valueOf(0.05), true, false, true, LocalDate.now().toEpochDay(), 1L);
        AssignmentAttribute percentAttr3 = new AssignmentAttribute("FIXEDSUMM", "FIXEDSUMM", ValueType.NUMERIC, String.valueOf(115.05), true, false, true, LocalDate.now().toEpochDay(), 1L);
        AssignmentAttribute formulaAttr = new AssignmentAttribute("FORMULA", "FORMULA", ValueType.FORMULA, "(COST*PERCENT)+(CREDITLIMITSIZE*CREDITPERCENT)+(FIXEDSUMM)", false, false, true, LocalDate.now().toEpochDay(), 1L);
        return new ArrayList<>(Arrays.asList(costAttr, percentAttr, percentAttr1, percentAttr2, percentAttr3, formulaAttr));
    }
}
