package com.magent.servicemodule.utils.ariphmeticbeans;

import com.magent.domain.AssignmentAttribute;
import com.magent.domain.ValueType;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * Created on 25.05.2016.
 */

@Component
public class ComissionCalculatorImpl implements ComissionCalculator {
    private final SpelParserConfiguration configuration = new SpelParserConfiguration(true, true);
    private final ExpressionParser parser = new SpelExpressionParser(configuration);

    /**
     * for more details how method works see ComissionCalculatorImplTest class
     * Example - if formula equals current String (COST*PERCENT)/5 , with values COST=20, PERCENT=0.1 result will be (20*0.1)/5=0.4
     *
     * @param attributesList        in list one attribute should have valueType with FORMULA enum
     * @param roundAfterZeroNumbers quantity of numbers, which will be rounded after zero
     * @return calculation result as number
     * @throws FormulaNotFound         if formula not present
     * @throws SpelEvaluationException if parser can't calculate value in present formula
     * @see ComissionCalculatorImpl#getFormula(List)
     */

    @Override
    public Number calculateCommission(List<AssignmentAttribute> attributesList, int roundAfterZeroNumbers) throws FormulaNotFound, SpelEvaluationException {
        String expression = getFormula(attributesList);
        //replace all values as String to values as numbers
        for (Map.Entry<String, Number> map : getValues(expression, attributesList).entrySet()) {
            //replace all values for SpelExpressionParser
            expression = expression.replaceAll(map.getKey(), String.valueOf(map.getValue()));
        }
        //calculate result in formula
        Expression parseExpression = parser.parseExpression(expression);
        //result
        BigDecimal d = new BigDecimal((Double) parseExpression.getValue()).setScale(roundAfterZeroNumbers, RoundingMode.DOWN);
        return d.doubleValue();
    }

    /**
     * current method created map with numbers from attributelist param and return it for future calculating
     *
     * @param formulaValue   as String
     * @param attributesList list with values
     * @return Map<String,Number> String - value in formula, Number value as number in formula
     * @see ComissionCalculatorImpl#calculateCommission(List, int) example
     */
    private Map<String, Number> getValues(String formulaValue, List<AssignmentAttribute> attributesList) {
        Map<String, Number> res = new TreeMap<>((Comparator<String>) (o1, o2) -> {
            if (o2.length() - o1.length() != 0) return o2.length() - o1.length();
            else return o2.compareTo(o1);
        });
        String variables[] = formulaValue.split("[()*/+-]");
        for (String s : variables) {
            if (!s.isEmpty()) {
                for (AssignmentAttribute attributes : attributesList) {
                    if (s.equals(attributes.getName())) {
                        res.put(s, Double.parseDouble(attributes.getValue()));
                    }
                }
            }
        }
        return res;
    }

    /**
     * current method searching for  ValueType.FORMULA enum and return value AssignmentAttribute#getValue() for calculating
     *
     * @param attributeList AssignmentAttribute list
     * @return Formula as String with values for calculating formula
     * @throws FormulaNotFound if list didn't contain ValueType.Formula enum
     * @see AssignmentAttribute
     * @see ValueType#FORMULA
     * @see ComissionCalculatorImpl#calculateCommission(List, int)
     */
    private String getFormula(List<AssignmentAttribute> attributeList) throws FormulaNotFound {

        for (AssignmentAttribute attribute : attributeList) {
            if (attribute.getValueType().equals(ValueType.FORMULA)) return attribute.getValue();

        }
        throw new FormulaNotFound(" formula not present in current attribute list ");
    }

    public static class FormulaNotFound extends Exception {
        public FormulaNotFound(String s) {
        }
    }
}
