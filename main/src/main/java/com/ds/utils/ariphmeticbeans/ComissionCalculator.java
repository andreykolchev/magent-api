package com.ds.utils.ariphmeticbeans;

import com.ds.domain.AssignmentAttribute;

import java.util.List;

/**
 * Created by artomov.ihor on 25.05.2016.
 */
public interface ComissionCalculator {
    /**
     * if in AssignmentAttribute some of value not present method throws SpelEvaluationException
     * percents enters as number not as percent: for example if operator whants 2% percent he should enter in value 0.02
     * @param attributesList in list one attribute should have valueType with FORMULA enum
     * @return cost of commission cost as number
     */
    Number calculateCommission(List<AssignmentAttribute> attributesList,int roundAfterZeroNumbers) throws ComissionCalculatorImpl.FormulaNotFound;
}
