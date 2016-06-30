package com.ds.service;

import com.ds.domain.Account;
import com.ds.domain.Transactions;
import com.ds.domain.User;
import com.ds.repository.AccountRepository;
import com.ds.repository.TransactionRepository;
import com.ds.service.interfaces.TransactionService;
import com.ds.utils.comparators.TransactionComparator;
import com.ds.utils.dateutils.DateUtils;
import com.ds.utils.xlsutil.interfaces.BalanceUploaderXlsWriter;
import com.ds.utils.xlsutil.interfaces.CsvReader;
import com.ds.utils.xlsutil.interfaces.TransactionsXlsReader;
import com.ds.utils.xlsutil.interfaces.TransactionsXlsWriter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by artomov.ihor on 31.05.2016.
 */
@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionsXlsReader xlsReader;

    @Autowired
    private CsvReader csvReader;
    @Autowired
    private DateUtils dateUtils;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BalanceUploaderXlsWriter balanceUploaderXlsWriter;

    @Autowired
    private TransactionComparator comparators;

    @Autowired
    private TransactionsXlsWriter transactionsXlsWriter;

    @Value("${tmp.excel.file}")
    private String uploadPath;
    private DateFormat df = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateTrasnactionsTable(MultipartFile multipartFile, String separatorSing, String fullFileName) throws FileNotSupportedException, IOException, ParseException, TransactionsXlsReader.NotCorrectXLSFileContent, CsvReader.NotCorrectCsvFileContent {
        if (isFormatCorrect(fullFileName, separatorSing)) {
            File uploadedFile = saveAsTmpFileAndReturn(multipartFile, fullFileName);
            List<Transactions> transactionsList = null;
            if (isXlsFormat(fullFileName)) {
                transactionsList = xlsReader.readFromExcel(uploadedFile);
            } else {
                transactionsList = csvReader.readFromCsv(uploadedFile, separatorSing);
            }
            if (transactionsList == null)
                throw new FileNotSupportedException("transaction file is Empty");

            transactionLogic(transactionsList);
        }
        return true;
    }

    //create xls file as balance report for current users
    @Override
    @Transactional(readOnly = true)
    public byte[] createXlsReportBalance(List<User> users) throws IOException {
        return balanceUploaderXlsWriter.createXlsReport(users);
    }

    //date in format yyyy-MM-dd
    @Override
    @Transactional(readOnly = true)
    public byte[] createTransactionReportByDateRequest(String yyyyMMdd) throws ParseException, IOException {
        return createTransactionReport(yyyyMMdd, yyyyMMdd);
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] createTransactionReportByPeriod(String date1, String date2) throws ParseException, IOException {
        return createTransactionReport(date1, date2);
    }

    //method checks correct file format
    private boolean isFormatCorrect(String fullFileName, String separatorSing) throws FileNotSupportedException {
        String[] tmp = fullFileName.split("\\.");
        boolean res = tmp[1].equalsIgnoreCase("xls");
        boolean isCsv = (tmp[1].equalsIgnoreCase("csv") & separatorSing.length() == 1);
        if (!res && !isCsv) throw new FileNotSupportedException("not supported file extension");
        //if (!isCsv) throw new FileNotSupportedException("not supported file extension");
        return true;
    }

    //decide which bean use for reading
    private boolean isXlsFormat(String fullFileName) {
        String[] tmp = fullFileName.split("\\.");
        return tmp[1].equalsIgnoreCase("xls");
    }

    //saved file in tmp directory not depended is operation successful or not
    @SuppressFBWarnings("PATH_TRAVERSAL_IN")
    private File saveAsTmpFileAndReturn(MultipartFile multipartFile, String fullFileName) throws IOException {
        String[] tmp = fullFileName.split("\\.");
        File file = new File(uploadPath + "tmp_upload_" + df.format(new Date()) + "." + tmp[1]);
        Files.write(Paths.get(file.getPath()), multipartFile.getBytes());
        return file;
    }

    private void transactionLogic(List<Transactions> transactionsList) {
        List<Transactions> presentTransactionsInDb = transactionRepository.findAll();
        //getting new transactions which present in upload file
        Collection<Transactions> newSameTransactions = new ArrayList<>(transactionsList);
        newSameTransactions.retainAll(presentTransactionsInDb);
        //geting old transactions
        Collection<Transactions> oldSameTransactions = leftOnlyOldSameTransaction(presentTransactionsInDb, newSameTransactions);
        //getting new transactions which not present in db
        Collection<Transactions> newTransactions = new ArrayList<>(transactionsList);
        newTransactions.removeAll(newSameTransactions);
        //do financial logic
        returnOldOperations(new ArrayList<>(oldSameTransactions));
        writeInDb(new ArrayList<>(newSameTransactions));
        writeInDb(new ArrayList<>(newTransactions));
    }

    //returns previous accounts balances before this transactions
    private void returnOldOperations(List<Transactions> transactionsList) {
        for (Transactions transactions : transactionsList) {
            Account account = accountRepository.findOne(transactions.getAccount_number());
            if (transactions.isIncrement()) {
                account.setAccountBalance((account.getAccountBalance() - transactions.getSumm().doubleValue()));
            }
            if (!transactions.isIncrement()) {
                account.setAccountBalance((account.getAccountBalance() + transactions.getSumm().doubleValue()));
            }
            accountRepository.save(account);
            transactionRepository.delete(transactions);
        }
    }

    //write in db transactions
    private void writeInDb(List<Transactions> transactionsList) {
        for (Transactions transactions : transactionsList) {
            Account account = accountRepository.findOne(transactions.getAccount_number());
            if (transactions.isIncrement())
                account.setAccountBalance((account.getAccountBalance() + transactions.getSumm().doubleValue()));
            if (!transactions.isIncrement())
                account.setAccountBalance((account.getAccountBalance() - transactions.getSumm().doubleValue()));
            accountRepository.save(account);
            transactionRepository.save(transactions);
        }
    }

    private List<Transactions> leftOnlyOldSameTransaction(Collection<Transactions> presentInDb, Collection<Transactions> newSameTrans) {
        List<Transactions> transactionsList = new ArrayList<>();
        for (Transactions transactions : newSameTrans) {
            for (Transactions preset : presentInDb) {
                if (transactions.equals(preset)) transactionsList.add(preset);
            }
        }
        return transactionsList;
    }

    private byte[] createTransactionReport(String date1, String date2) throws ParseException, IOException {
        String oneDayMinusDate = dateUtils.getOneDayMinus(date1);
        String oneDayPlusDate = dateUtils.getOneDayPlus(date2);
        List<Transactions> transactionsList = transactionRepository.getByTwoDates(dateUtils.getformatOnlyDays().parse(oneDayMinusDate), dateUtils.getformatOnlyDays().parse(oneDayPlusDate));
        Collections.sort(transactionsList, comparators.getByAccountAndByDate());
        return transactionsXlsWriter.createXlsReport(transactionsList);
    }
}
