package com.ds.utilbeanstest;

import com.ds.config.ServiceConfig;
import com.ds.domain.Transactions;
import com.ds.utils.xlsutil.interfaces.CsvReader;
import com.ds.utils.xlsutil.interfaces.TransactionsXlsReader;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.net.URI;
import java.util.List;

/**
 * CsvReaderImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>????. 30, 2016</pre>
 */
public class CsvReaderImplTest extends ServiceConfig {

    @Autowired
    private CsvReader csvReader;
    @Autowired
    TransactionsXlsReader transactionsXlsReader;

    /**
     * Method: readFromCsv(File file, String separateSign)
     */
    @Test
    public void testReadFromExcel() throws Exception {
        File csvFile = new File(URI.create(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("csvtestdata/test.csv"))));
        List<Transactions> transactionsList = csvReader.readFromCsv(csvFile, ";");

        File xlsFile = new File(URI.create(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("xlstestdata/testBookSimplePositive.xls"))));
        List<Transactions> transactionsListXls = transactionsXlsReader.readFromExcel(xlsFile);
        Assert.assertEquals(transactionsList.size(), transactionsListXls.size());
        for (int i = 0; i < transactionsList.size(); i++) {
            Assert.assertEquals(transactionsList.get(i),transactionsListXls.get(i));
        }
    }


} 
