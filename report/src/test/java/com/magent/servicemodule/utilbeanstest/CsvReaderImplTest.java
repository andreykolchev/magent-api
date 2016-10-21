package com.magent.servicemodule.utilbeanstest;

import com.magent.config.ServiceConfig;
import com.magent.domain.Transactions;
import com.magent.reportmodule.utils.xlsutil.interfaces.CsvReader;
import com.magent.reportmodule.utils.xlsutil.interfaces.TransactionsXlsReader;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
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
        File csvFile = new File(resourcePath+"csvtestdata/test.csv");
        List<Transactions> transactionsList = csvReader.readFromCsv(csvFile, ";");

        File xlsFile = new File(resourcePath+"xlstestdata/testBookSimplePositive.xls");
        List<Transactions> transactionsListXls = transactionsXlsReader.readFromExcel(xlsFile);
        Assert.assertEquals(transactionsList.size(), transactionsListXls.size());
        for (int i = 0; i < transactionsList.size(); i++) {
            Assert.assertEquals(transactionsList.get(i),transactionsListXls.get(i));
        }
    }


} 
