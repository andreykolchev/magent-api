package com.magent.reportmodule.reportservice.interfaces;

import com.magent.domain.User;
import com.magent.reportmodule.utils.xlsutil.interfaces.CsvReader;
import com.magent.reportmodule.utils.xlsutil.interfaces.TransactionsXlsReader;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by artomov.ihor on 31.05.2016.
 */
public interface TransactionService {
    /**
     * @param multipartFile - file with descriptions of transactions with hard file structure
     * @apiNote - service decide which type of file user uploaded and update transactions with account balance.
     * - unique transaction considered if it has same account number and full date
     * @see TransactionsXlsReader bean for structure of file
     * @see CsvReader bean
     */
    boolean updateTrasnactionsTable(MultipartFile multipartFile, String separatorSing, String fullFileName) throws FileNotSupportedException, IOException, ParseException, TransactionsXlsReader.NotCorrectXLSFileContent, CsvReader.NotCorrectCsvFileContent;

    byte[] createXlsReportBalance(List<User> userList) throws IOException;
    //dateFormat yyyy-MM-dd
    byte[] createTransactionReportByDateRequest(String yyyyMMdd) throws ParseException, IOException;
    //dateFormat yyyy-MM-dd
    byte[]createTransactionReportByPeriod(String date1,String date2) throws ParseException, IOException;

    class FileNotSupportedException extends Exception {
        public FileNotSupportedException(String message) {
            super(message);
        }
    }
}
