package com.ds.utils.comparators;

import com.ds.domain.Transactions;

import java.util.Comparator;

/**
 * Created by artomov.ihor on 03.06.2016.
 */

public interface TransactionComparator {
      Comparator<Transactions>getByAccountAndByDate();
}
