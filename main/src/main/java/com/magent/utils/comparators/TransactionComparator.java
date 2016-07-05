package com.magent.utils.comparators;

import com.magent.domain.Transactions;

import java.util.Comparator;

/**
 * Created by artomov.ihor on 03.06.2016.
 */

public interface TransactionComparator {
      Comparator<Transactions>getByAccountAndByDate();
}
