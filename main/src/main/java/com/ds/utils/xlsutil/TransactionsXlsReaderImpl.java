package com.ds.utils.xlsutil;

import com.ds.domain.Transactions;
import com.ds.utils.xlsutil.interfaces.TransactionsXlsReader;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by artomov.ihor on 27.05.2016.
 */
@Component
public class TransactionsXlsReaderImpl implements TransactionsXlsReader {
    private DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    @Override
    public List<Transactions> readFromExcel(File file) throws IOException, NotCorrectXLSFileContent, ParseException {
        //read file
        HSSFWorkbook excelFile = new HSSFWorkbook(new FileInputStream(file));
        //get first page from excel
        HSSFSheet sheet = excelFile.getSheetAt(0);
        // получаем Iterator по всем строкам в листе
        Iterator<Row> rowIterator = sheet.iterator();

        Row firstRow = rowIterator.next();
        validateFirstRow(firstRow);

        List<Transactions> transactionsList = getTransactionsFromXLS(rowIterator);
        excelFile.close();
        return transactionsList;
    }

    @SuppressFBWarnings("NS_DANGEROUS_NON_SHORT_CIRCUIT")
    private List<Transactions> getTransactionsFromXLS(Iterator<Row> rowIterator) throws NotCorrectXLSFileContent, ParseException {
        List<Transactions> transactionsList = new ArrayList<>();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Long accountId;
            Date transactionDate = null;
            boolean isIncrement;
            BigDecimal summOfTransaction = null;

            if (row.getCell(0) != null & row.getCell(1) != null &
                    row.getCell(2) != null & row.getCell(3) != null) {
                if (!row.getCell(3).getStringCellValue().equals("-") && !row.getCell(3).getStringCellValue().equals("+")) {
                    throw new NotCorrectXLSFileContent("transaction operation increment or decrement not defined");
                }
                Number accountNumber = row.getCell(0).getNumericCellValue();
                accountId = accountNumber.longValue();
                summOfTransaction = parseSummOfTransaction(row);
                transactionDate = parseTransactionDate(row);
                isIncrement = row.getCell(3).getStringCellValue().equals("-") ? false : true;
                transactionsList.add(new Transactions(accountId, transactionDate, isIncrement, summOfTransaction));
            }
        }
        return transactionsList;
    }

    private void validateFirstRow(Row firstRow) throws NotCorrectXLSFileContent {
        if (firstRow.getCell(0).getCellType() != 1)
            throw new NotCorrectXLSFileContent(" not valid Content in first Row, for current row allowed only string value and contents should not be empty ");
        if (firstRow.getCell(1).getCellType() != 1)
            throw new NotCorrectXLSFileContent(" not valid Content in first Row, for current row allowed only string value and contents should not be empty ");
        if (firstRow.getCell(2).getCellType() != 1)
            throw new NotCorrectXLSFileContent(" not valid Content in first Row, for current row allowed only string value and contents should not be empty ");
        if (firstRow.getCell(3).getCellType() != 1)
            throw new NotCorrectXLSFileContent(" not valid Content in first Row, for current row allowed only string value and contents should not be empty ");
    }


    private BigDecimal parseSummOfTransaction(Row row) {
        BigDecimal summOfTransaction = null;
        try {
            summOfTransaction = BigDecimal.valueOf(Double.parseDouble(row.getCell(1).getStringCellValue()));
        } catch (IllegalStateException e) {
        }
        if (summOfTransaction == null) {
            summOfTransaction = BigDecimal.valueOf(row.getCell(1).getNumericCellValue());
        }
        return summOfTransaction;
    }

    private Date parseTransactionDate(Row row) throws ParseException {
        Date transactionDate = null;
        try {
            transactionDate = row.getCell(2).getDateCellValue();
        } catch (IllegalStateException e) {
        }
        if (transactionDate == null) {
            transactionDate = df.parse(row.getCell(2).getStringCellValue());
        }
        return transactionDate;
    }
}
