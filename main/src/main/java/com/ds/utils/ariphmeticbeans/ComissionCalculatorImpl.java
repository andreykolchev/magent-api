package com.ds.utils.ariphmeticbeans;

import com.ds.domain.AssignmentAttribute;
import com.ds.domain.ValueType;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
 * Created by artomov.ihor on 25.05.2016.
 */
@SuppressFBWarnings({"SPEL_INJECTION", "SPEL_INJECTION"})
@Component
public class ComissionCalculatorImpl implements ComissionCalculator {
    private final SpelParserConfiguration configuration=new SpelParserConfiguration(true,true);
    private final ExpressionParser parser = new SpelExpressionParser(configuration);

    @SuppressFBWarnings("SPEL_INJECTION")
    @Override
    public Number calculateCommission(List<AssignmentAttribute> attributesList, int roundAfterZeroNumbers) throws FormulaNotFound, SpelEvaluationException {
        String expression = getFormula(attributesList);
        for (Map.Entry<String, Number> map : getValues(expression, attributesList).entrySet()) {
            expression = expression.replaceAll(map.getKey(), String.valueOf(map.getValue()));
        }
        Expression parseExpression = parser.parseExpression(expression);
        //result
        BigDecimal d = new BigDecimal((Double) parseExpression.getValue()).setScale(roundAfterZeroNumbers, RoundingMode.DOWN);
        return d.doubleValue();
    }

    private Map<String, Number> getValues(String formulaValue, List<AssignmentAttribute> attributesList) {
        Map<String, Number> res = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o2.length() - o1.length() != 0) return o2.length() - o1.length();
                else return o2.compareTo(o1);
            }
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
