package com.ds.utils.comparators;

import com.ds.domain.Transactions;
import org.springframework.stereotype.Component;

import java.util.Comparator;

/**
 * Created by artomov.ihor on 03.06.2016.
 */
@Component
public class TransactionComparatorImpl implements TransactionComparator {
    @Override
    public Comparator<Transactions> getByAccountAndByDate() {
        Comparator<Transactions> byAccountAndByDate = new Comparator<Transactions>() {
            @Override
            public int compare(Transactions o1, Transactions o2) {
                int byAccount = o1.getAccount_number().intValue() - o2.getAccount_number().intValue();
                if (byAccount == 0) {
                    return o1.getTransactionDate().compareTo(o2.getTransactionDate());
                }
                return byAccount;
            }
        };
        return byAccountAndByDate;
    }
}
