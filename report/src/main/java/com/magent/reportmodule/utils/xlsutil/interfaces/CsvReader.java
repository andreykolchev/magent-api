package com.magent.reportmodule.utils.xlsutil.interfaces;

import com.magent.domain.Transactions;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by artomov.ihor on 30.05.2016.
 */
public interface CsvReader {
    /**
     *
     * @param file  file saved as dublicate before reading
     * @param separateSign - sign which accepted as separating sign
     * @return list of transactions
     * @see Transactions class
     */
    List<Transactions> readFromCsv(File file, String separateSign) throws IOException, NotCorrectCsvFileContent, ParseException;
    class NotCorrectCsvFileContent extends Exception {
        public NotCorrectCsvFileContent(String message) {
            super(message);
        }
    }
}
