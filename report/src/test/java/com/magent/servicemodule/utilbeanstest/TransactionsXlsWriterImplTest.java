package com.magent.servicemodule.utilbeanstest;

import com.magent.config.ServiceConfig;
import com.magent.domain.Transactions;
import com.magent.reportmodule.utils.xlsutil.interfaces.TransactionsXlsReader;
import com.magent.reportmodule.utils.xlsutil.interfaces.TransactionsXlsWriter;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.List;

/**
 * TransactionsXlsWriterImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>????. 27, 2016</pre>
 */
public class TransactionsXlsWriterImplTest extends ServiceConfig {

    @Value("${upload.file.path}")
    private String uploadPath;

    @Autowired
    TransactionsXlsWriter xlsWriter;

    @Autowired
    TransactionsXlsReader transactionsXlsReader;

    /**
     * Method: createXlsReportBalance(List<Transactions> transactionsList)
     */
    @Test
    public void testCreateXlsReport() throws Exception {
        File xlsFile = new File(URI.create(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("xlstestdata/testBookSimplePositive.xls"))));
        List<Transactions> transactionsList = transactionsXlsReader.readFromExcel(xlsFile);
        byte[] res = xlsWriter.createXlsReport(transactionsList);
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(uploadPath + "upload.xls")));
        stream.write(res);
        stream.close();
        File newXlsFile = new File(uploadPath + "upload.xls");
        List<Transactions> transactionsListRes = transactionsXlsReader.readFromExcel(newXlsFile);

        Assert.assertEquals(transactionsListRes.size(), transactionsList.size());
        for (int i = 0; i < transactionsListRes.size(); i++) {
            Assert.assertEquals(transactionsListRes.get(i), transactionsList.get(i));
        }
    }


} 
