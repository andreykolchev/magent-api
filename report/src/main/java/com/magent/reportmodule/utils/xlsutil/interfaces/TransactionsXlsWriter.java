package com.magent.reportmodule.utils.xlsutil.interfaces;

import com.magent.domain.Transactions;

import java.io.IOException;
import java.util.List;

/**
 * Created by artomov.ihor on 27.05.2016.
 */
public interface TransactionsXlsWriter extends XlsCreator {

    /**
     * @param transactionsList - list of transactions from ds_transactions
     * @return xls file
     */
    public byte[] createXlsReport(List<Transactions> transactionsList) throws IOException;

}
