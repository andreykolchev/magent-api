package com.magent.servicemodule.utilbeanstest;

import com.magent.config.ServiceConfig;
import com.magent.domain.Transactions;
import com.magent.reportmodule.utils.xlsutil.interfaces.TransactionsXlsReader;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.util.List;

/**
 * TransactionsXlsReaderImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>????. 27, 2016</pre>
 */
public class TransactionsXlsReaderImplTest extends ServiceConfig {

    @Autowired
    TransactionsXlsReader transactionsXlsReader;

    /**
     * Method: readFromCsv(File file)
     * simple positive test
     */
    @Test
    public void testReadFromExcel() throws Exception {
        File xlsFile = new File(URI.create(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("xlstestdata/testBookSimplePositive.xls"))));
        List<Transactions> transactionsList = transactionsXlsReader.readFromExcel(xlsFile);
        Assert.assertNotNull(transactionsList);
        Assert.assertEquals(transactionsList.size(), 2);

    }

    @Test
    public void testReadFromExcel50K() throws Exception {
        File xlsFile = new File(URI.create(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("xlstestdata/testBoolPositive50K.xls"))));
        List<Transactions> transactionsList = transactionsXlsReader.readFromExcel(xlsFile);
        Assert.assertNotNull(transactionsList);
        Assert.assertEquals(transactionsList.size(), 50000);
    }

    @Test(expected = TransactionsXlsReader.NotCorrectXLSFileContent.class)
    public void testReadFromExcelWithoutContent() throws IOException, TransactionsXlsReader.NotCorrectXLSFileContent, ParseException {
        File xlsFile = new File(URI.create(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("xlstestdata/testBookWithoutContents.xls"))));
        List<Transactions> transactionsList = transactionsXlsReader.readFromExcel(xlsFile);
    }

    @Test(expected = TransactionsXlsReader.NotCorrectXLSFileContent.class)
    public void testReadFromExcelContentsNotCorrect() throws IOException, TransactionsXlsReader.NotCorrectXLSFileContent, ParseException {
        File xlsFile = new File(URI.create(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("xlstestdata/testBookContentsNotCorrect.xls"))));
        List<Transactions> transactionsList = transactionsXlsReader.readFromExcel(xlsFile);
    }

    @Test(expected = TransactionsXlsReader.NotCorrectXLSFileContent.class)
    public void testBookContentsValidSomeOfDataNotValid() throws IOException, TransactionsXlsReader.NotCorrectXLSFileContent, ParseException {
        File xlsFile = new File(URI.create(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("xlstestdata/testBookContentsValidSomeOfDataNotValid.xls"))));
        List<Transactions> transactionsList = transactionsXlsReader.readFromExcel(xlsFile);
    }
    @Test(expected = IllegalStateException.class)
    public void testBookFirstCellNotValid() throws IOException, TransactionsXlsReader.NotCorrectXLSFileContent, ParseException {
        File xlsFile = new File(URI.create(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("xlstestdata/testBookFirstCellNotValid.xls"))));
        List<Transactions> transactionsList = transactionsXlsReader.readFromExcel(xlsFile);
    }

    @Test(expected = ParseException.class)
    public void testBookThirdCellNotValid() throws IOException, TransactionsXlsReader.NotCorrectXLSFileContent, ParseException {
        File xlsFile = new File(URI.create(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("xlstestdata/testBookThirdCellNotValid.xls"))));
        List<Transactions> transactionsList = transactionsXlsReader.readFromExcel(xlsFile);
    }

    @Test(expected = TransactionsXlsReader.NotCorrectXLSFileContent.class)
    public void testBookFourthCellNotValid() throws IOException, TransactionsXlsReader.NotCorrectXLSFileContent, ParseException {
        File xlsFile = new File(URI.create(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("xlstestdata/testBookFourthCellNotValid.xls"))));
        List<Transactions> transactionsList = transactionsXlsReader.readFromExcel(xlsFile);
    }

} 
