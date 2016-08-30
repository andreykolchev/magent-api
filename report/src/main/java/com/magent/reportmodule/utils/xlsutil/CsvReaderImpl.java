package com.magent.reportmodule.utils.xlsutil;

import com.magent.domain.Transactions;
import com.magent.reportmodule.utils.xlsutil.interfaces.CsvReader;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by artomov.ihor on 30.05.2016.
 */
@Component
public class CsvReaderImpl implements CsvReader {
    private DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    @SuppressFBWarnings({"DM_DEFAULT_ENCODING", "NP_DEREFERENCE_OF_READLINE_VALUE"})
    @Override
    public List<Transactions> readFromCsv(File file, String separateSign) throws IOException, NotCorrectCsvFileContent, ParseException {
        List<Transactions> transactionsList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        while (reader.ready()) {

            String[] values = reader.readLine().split(separateSign);
            if (values.length != 4) {
                throw new NotCorrectCsvFileContent("transaction line don't have 4 values");
            }
            Long accountNumber = Long.valueOf(values[0]);
            BigDecimal summOfTransaction = new BigDecimal(Double.valueOf(values[1]));
            Date transactionDate = df.parse(values[2]);
            boolean isIncrement = values[3].equals("-") ? false : true;
            transactionsList.add(new Transactions(accountNumber, transactionDate, isIncrement, summOfTransaction));
        }
        reader.close();
        return transactionsList;
    }
}
