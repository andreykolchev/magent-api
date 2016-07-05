package com.magent.service;

import com.magent.config.ServiceConfig;
import com.magent.domain.Account;
import com.magent.domain.Transactions;
import com.magent.domain.User;
import com.magent.repository.AccountRepository;
import com.magent.repository.UserRepository;
import com.magent.service.interfaces.TransactionService;
import com.magent.utils.xlsutil.interfaces.CsvReader;
import com.magent.utils.xlsutil.interfaces.TransactionsXlsReader;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;

/**
 * TransactionServiceImpl Tester.
 *
 * @author artomov.ihor
 * @version 1.0
 * @since <pre>May. 31, 2016</pre>
 */
public class TransactionServiceImplTest extends ServiceConfig {

    @Value("${tmp.excel.file}")
    private String uploadPath;

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    TransactionsXlsReader transactionsXlsReader;


    /**
     * Method: updateTrasnactionsTable(MultipartFile multipartFile, String separatorSing)
     */
    @Test
    @Sql("classpath:data.sql")
    public void testUpdateTrasnactionsTable() throws Exception {
        Long acountNumberExpected = 999999999999L;
        Double expectedBalanceAfterUpload = 1900.04;
        MultipartFile multipartFile = new MockMultipartFile("test.xls", Files.readAllBytes(Paths.get(uploadPath + "updateTransactionServiceTest.xls")));
        transactionService.updateTrasnactionsTable(multipartFile, "", "test.xls");
        Account account = accountRepository.findOne(acountNumberExpected);
        Assert.assertEquals(expectedBalanceAfterUpload, account.getAccountBalance());
    }

    @Test
    @Sql("classpath:data.sql")
    public void testUpdateTransactionTable2() throws IOException, TransactionService.FileNotSupportedException, TransactionsXlsReader.NotCorrectXLSFileContent, CsvReader.NotCorrectCsvFileContent, ParseException {
        Long acountNumberExpected1 = 999999999999L;
        Long acountNumberExpected2 = 999999999998L;
        Double expectedAccount1 = 1696.04;
        Double expectedAccount2 = 1582.82;
        MultipartFile multipartFile = new MockMultipartFile("test.xls", Files.readAllBytes(Paths.get(uploadPath + "updateTransactionSecond.xls")));
        transactionService.updateTrasnactionsTable(multipartFile, "", "test.xls");

        Account account1 = accountRepository.findOne(acountNumberExpected1);
        Account account2 = accountRepository.findOne(acountNumberExpected2);
        Assert.assertEquals(expectedAccount1, account1.getAccountBalance());
        Assert.assertEquals(expectedAccount2, account2.getAccountBalance());
    }

    @Test
    @Sql("classpath:data.sql")
    public void updateTransactionTableCsv() throws IOException, TransactionService.FileNotSupportedException, TransactionsXlsReader.NotCorrectXLSFileContent, CsvReader.NotCorrectCsvFileContent, ParseException {
        Long accountNumberExpected = 999999999999L;
        Double expectedBalance = 1537.08;
        MultipartFile multipartFile = new MockMultipartFile("test.xls", Files.readAllBytes(Paths.get(uploadPath + "test.csv")));
        transactionService.updateTrasnactionsTable(multipartFile, ";", "test.csv");
        //test
        Account account = accountRepository.findOne(accountNumberExpected);
        Assert.assertEquals(expectedBalance, account.getAccountBalance());
    }

    @Test
    @Sql("classpath:data.sql")
    public void createXlsReport() throws IOException {
        List<User> userList = userRepository.getAllUsersWithAccount();
        byte[] filesByte = transactionService.createXlsReportBalance(userList);
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(uploadPath + "testReport.xls")));

        stream.write(filesByte);
        stream.close();
        //test
        Assert.assertTrue(new File(uploadPath + "testReport.xls").exists());

        Files.delete(Paths.get(uploadPath + "testReport.xls"));

    }

    @Test
    @Sql("classpath:data.sql")
    public void createTransactionReportByDateRequestTest() throws IOException, ParseException, TransactionsXlsReader.NotCorrectXLSFileContent {
        byte[] filesByte = transactionService.createTransactionReportByDateRequest("2016-04-10");
        File fileTosave = new File(uploadPath + "transactionReport.xls");
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(fileTosave));
        stream.write(filesByte);
        stream.close();

        List<Transactions> transactionsList = transactionsXlsReader.readFromExcel(fileTosave);
        int expectedSizeOfTransactions = 3;
        Assert.assertEquals(expectedSizeOfTransactions, transactionsList.size());
        Files.delete(fileTosave.toPath());
    }

    @Test
    @Sql("classpath:data.sql")
    public void createTransactionReportByPeriodtest() throws IOException, ParseException, TransactionsXlsReader.NotCorrectXLSFileContent {
        String date1 = "2016-04-10";
        String date2 = "2016-04-14";
        byte[] filesByte = transactionService.createTransactionReportByPeriod(date1, date2);
        File fileTosave = new File(uploadPath + "transactionReportPeriod.xls");
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(fileTosave));
        stream.write(filesByte);
        stream.close();

        int expectedSizeOfTransactions = 4;
        List<Transactions> transactionsList = transactionsXlsReader.readFromExcel(fileTosave);
        Assert.assertEquals(expectedSizeOfTransactions, transactionsList.size());
        Files.delete(fileTosave.toPath());


    }

} 
