package com.ds.utils.xlsutil;

import com.ds.domain.Transactions;
import com.ds.utils.xlsutil.interfaces.TransactionsXlsWriter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by artomov.ihor on 27.05.2016.
 */

@Component
public class TransactionsXlsWriterImpl implements TransactionsXlsWriter {

    private final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    @SuppressFBWarnings("UWF_NULL_FIELD")
    private CellStyle csBold = null;
    private CellStyle cs = null;


    @Override
    public byte[] createXlsReport(List<Transactions> transactionsList) throws IOException {
        Workbook excelFile = createXlsFile();
        Sheet sheet = getDefaultSheet(excelFile);
        setCellStyles(excelFile);
        sheet.setColumnWidth(3, 7500);
        createFirstRow(sheet);
        int rowCounter = 1;
        //logic to write
        for (Transactions transactions : transactionsList) {
            Row row = sheet.createRow(rowCounter);

            row.createCell(0).setCellValue(transactions.getAccount_number());
            Cell cell1 = row.createCell(1);
            cell1.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell1.setCellValue(String.valueOf(transactions.getSumm()));

            Cell cell2 = row.createCell(2);
            cell2.setCellValue(DATE_FORMAT.format(transactions.getTransactionDate()));

            String sing = transactions.isIncrement() ? "+" : "-";
            row.createCell(3).setCellValue(sing);
            rowCounter++;
        }
        return createXlsAsArray(excelFile);
    }

    private void createFirstRow(Sheet sheet) {
        Row firstRow = sheet.createRow(0);

        Cell cell1 = firstRow.createCell(0);
        cell1.setCellValue("Account number");
        cell1.setCellStyle(csBold);

        Cell cell2 = firstRow.createCell(1);
        cell2.setCellValue("Transaction summ");
        cell2.setCellStyle(csBold);

        Cell cell3 = firstRow.createCell(2);
        cell3.setCellValue("Transaction date");
        cell3.setCellStyle(csBold);

        Cell cell4 = firstRow.createCell(3);
        cell4.setCellValue("Sign of transaction +/-");
        cell4.setCellStyle(csBold);

    }

    private void setCellStyles(Workbook wb) {
        //font size 10
        Font f = wb.createFont();
        f.setFontHeightInPoints((short) 10);

        //Simple style
        cs = wb.createCellStyle();
        cs.setFont(f);

        //Bold Fond
        Font bold = wb.createFont();
        bold.setBoldweight(Font.BOLDWEIGHT_BOLD);
        bold.setFontHeightInPoints((short) 10);

        //Bold style
        csBold = wb.createCellStyle();
        csBold.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        csBold.setFont(bold);

    }
}
