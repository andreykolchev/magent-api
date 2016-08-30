package com.magent.reportmodule.utils.xlsutil;

import com.magent.domain.Account;
import com.magent.domain.User;
import com.magent.reportmodule.utils.xlsutil.interfaces.BalanceUploaderXlsWriter;
import com.magent.repository.AccountRepository;
import com.magent.repository.UserRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * Created by artomov.ihor on 30.05.2016.
 */
@Component
public class BalanceUploaderXlsWriterImpl implements BalanceUploaderXlsWriter {
    private CellStyle cs = null;
    private CellStyle csBold = null;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public byte[] createXlsReport(List<User> userList) throws IOException {
        Workbook excelFile = createXlsFile();
        Sheet sheet = getDefaultSheet(excelFile);
        setCellStyles(excelFile);
        createFirstRow(sheet);

        int rowCounter = 1;
        //logic to write
        for (User users : userList) {
            User user = userRepository.findOne(users.getId());
            Row row = sheet.createRow(rowCounter);
            Account account = accountRepository.getByUserId(users.getId());
            row.createCell(0).setCellValue(user.getFirstName() + " " + user.getLastName());
            Cell cell1 = row.createCell(1);
            cell1.setCellType(Cell.CELL_TYPE_NUMERIC);

            cell1.setCellValue(String.valueOf(account.getAccountNumber()));

            Cell cell2 = row.createCell(2);
            cell2.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell2.setCellValue(account.getAccountBalance());

            rowCounter++;
        }
        return createXlsAsArray(excelFile);
    }

    private void createFirstRow(Sheet sheet) {
        Row firstRow = sheet.createRow(0);

        Cell cell1 = firstRow.createCell(0);
        cell1.setCellValue("User name");
        cell1.setCellStyle(csBold);

        Cell cell2 = firstRow.createCell(1);
        cell2.setCellValue("account number");
        cell2.setCellStyle(csBold);

        Cell cell3 = firstRow.createCell(2);
        cell3.setCellValue("balance");
        cell3.setCellStyle(csBold);

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
        //csBold.setBorderBottom(CellStyle.BORDER_THIN);
        csBold.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        csBold.setFont(bold);

    }

}
