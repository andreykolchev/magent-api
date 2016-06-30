package com.ds.utils.xlsutil.interfaces;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by artomov.ihor on 17.06.2016.
 */
public interface XlsCreator {

    default Workbook createXlsFile() {
        return new HSSFWorkbook();
    }

    default Sheet getDefaultSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet();
        sheet.setColumnWidth(0, 7500);
        sheet.setColumnWidth(1, 7500);
        sheet.setColumnWidth(2, 7500);
        return sheet;
    }

    default byte[] createXlsAsArray(Workbook excelFile) throws IOException {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        excelFile.write(arrayOutputStream);
        excelFile.close();
        arrayOutputStream.close();
        return arrayOutputStream.toByteArray();
    }
}
