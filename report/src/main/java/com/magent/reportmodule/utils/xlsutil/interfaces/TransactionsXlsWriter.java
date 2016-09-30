package com.magent.reportmodule.utils.xlsutil.interfaces;

import com.magent.domain.Transactions;

import java.io.IOException;
import java.util.List;

/**
 * interface for write transactions in to xls file
 * Created by artomov.ihor on 27.05.2016.
 */
public interface TransactionsXlsWriter extends XlsCreator {

    /**
     * @param transactionsList - list of transactions from ds_transactions
     * @return xls file as byte array
     */
    byte[] createXlsReport(List<Transactions> transactionsList) throws IOException;

}
