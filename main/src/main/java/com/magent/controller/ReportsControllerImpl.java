package com.magent.controller;

import com.magent.controller.interfaces.GeneralController;
import com.magent.domain.User;
import com.magent.reportmodule.reportservice.interfaces.TransactionService;
import com.magent.reportmodule.utils.xlsutil.interfaces.CsvReader;
import com.magent.reportmodule.utils.xlsutil.interfaces.TransactionsXlsReader;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * controller for working with reports.
 */
@RestController
@RequestMapping("/reports")
public class ReportsControllerImpl implements GeneralController {
    @Autowired
    private TransactionService transactionService;

    @ApiOperation(value = "upload transactions file")
    @RequestMapping(method = RequestMethod.POST, value = "/transactions/upload")
    public ResponseEntity<String> uploadTransactions(
            @RequestParam(required = true, value = "file") MultipartFile xlsOrCsv,
            @RequestParam("fileName") String fileName,
            @RequestParam(value = "separateSign", required = false) String separateSign) throws TransactionService.FileNotSupportedException, TransactionsXlsReader.NotCorrectXLSFileContent, CsvReader.NotCorrectCsvFileContent, ParseException, IOException {
        boolean res = transactionService.updateTrasnactionsTable(xlsOrCsv, separateSign, fileName);
        String answ = res ? "upload success" : "upload failed";
        HttpStatus status = res ? HttpStatus.OK : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity(answ, status);
    }

    @ApiOperation(value = "create XLS report for current users with balance", notes = "request body should be UserList with user.id, creating XLS report for selected users")
    @RequestMapping(method = RequestMethod.POST, value = "/balance/create")
    public ResponseEntity<byte[]> getXlsReport(@RequestBody List<User> userList) throws IOException {
        byte[] response = transactionService.createXlsReportBalance(userList);
        return getDefaultResponce(response);
    }

    /**
     * @param date - date in format yyyy-MM-dd
     * @return byte[]array creating by apache poi for xls files
     * @throws IOException if can't create xls file
     */
    @ApiOperation(notes = "date MUST be in yyyy-MM-dd format, method returned byte[]array for xls format", value = "get XLS report for transactions by date")
    @RequestMapping(method = RequestMethod.GET, value = "/transactions/bydate")
    public ResponseEntity<byte[]> getXlsTransactionReportByDate(@RequestParam("date") String date) throws IOException, ParseException {
        byte[] response = transactionService.createTransactionReportByDateRequest(date);
        return getDefaultResponce(response);
    }

    @ApiOperation(notes = "date MUST be in yyyy-MM-dd format, method returned byte[]array for xls format", value = "get XLS report for transactions by selected period")
    @RequestMapping(method = RequestMethod.GET, value = "/transactions/byperiod")
    public ResponseEntity<byte[]> getXlsTransactionReportByPeriod(@RequestParam("date1") String date1,
                                                                  @RequestParam("date2") String date2) throws IOException, ParseException {
        byte[] response = transactionService.createTransactionReportByPeriod(date1, date2);
        return getDefaultResponce(response);
    }

    private ResponseEntity<byte[]>getDefaultResponce(byte[] response){
        HttpStatus status = (response.length == 0) ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(response, status);
    }
}
