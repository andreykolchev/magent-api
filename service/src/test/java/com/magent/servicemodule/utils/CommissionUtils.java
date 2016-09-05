package com.magent.servicemodule.utils;

import com.magent.domain.AssignmentAttribute;
import com.magent.domain.ValueType;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

/**
 * Created by artomov.ihor on 31.05.2016.
 */
public class CommissionUtils {
    public static Double getCommissionCost(List<AssignmentAttribute> attributeList) throws ChangeSetPersister.NotFoundException {
        Double commissionCost = null;
        for (AssignmentAttribute attribute : attributeList) {
            if (attribute.getValueType().equals(ValueType.COMMISSION_COST)) {
                commissionCost = Double.valueOf(attribute.getValue());
                return commissionCost;
            }
        }
        throw new ChangeSetPersister.NotFoundException();
    }
}
