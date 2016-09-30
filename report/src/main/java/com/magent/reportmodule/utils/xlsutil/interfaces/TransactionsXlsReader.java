package com.magent.reportmodule.utils.xlsutil.interfaces;

import com.magent.domain.Transactions;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * interface for reading from xls files
 * Created by artomov.ihor on 27.05.2016.
 */
public interface TransactionsXlsReader {
    /**
     * @param file - file should be in XLS format and with structure:
     *             1 - first row should have content;
     *             2 - from second row and till the end, file must have structure:
     *             a) - account number first cell
     *             b) - transaction sum second cell
     *             c) - transaction date third cell
     *             d) - sign "+" or "-" another signs not allowed
     * @return transaction list
     * @throws IOException
     * @throws NotCorrectXLSFileContent
     * @see Transactions
     */
    List<Transactions> readFromExcel(File file) throws IOException, NotCorrectXLSFileContent, ParseException;

    class NotCorrectXLSFileContent extends Exception {
        public NotCorrectXLSFileContent(String message) {
            super(message);
        }
    }
}
