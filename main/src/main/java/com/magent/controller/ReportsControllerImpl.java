package com.magent.controller;

import com.magent.controller.interfaces.GeneralController;
import com.magent.domain.User;
import com.magent.service.interfaces.GeneralService;
import com.magent.service.interfaces.TransactionService;
import com.magent.utils.xlsutil.interfaces.CsvReader;
import com.magent.utils.xlsutil.interfaces.TransactionsXlsReader;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created  on 03.06.2016.
 */
@RestController
@RequestMapping("/reports")
public class ReportsControllerImpl implements GeneralController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    @Qualifier("userGeneralService")
    private GeneralService userServiceGen;


    @ApiOperation(notes = "http://10.77.6.237:8010/job/ds_api/Upload_transactions_Web_Test_interface/", value = "upload transactions file")
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
     * @throws IOException
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
    @Autowired
    private ApplicationContext appContext;
    @Autowired
    private DataSource dataSource;

    @RequestMapping(value = "/jasper-pdf",method = RequestMethod.GET)
    public ModelAndView getJasperPdfTest(HttpServletResponse response) throws NotFoundException, JRException, IOException {
        final JasperReportsPdfView view = new JasperReportsPdfView();
        view.setUrl("classpath:/reports/user_table_report.jrxml");
        view.setApplicationContext(appContext);
        view.setJdbcDataSource(dataSource);
        Map<String, Object> params = new HashMap<>();
        return new ModelAndView(view, params);
    }

    private ResponseEntity<byte[]>getDefaultResponce(byte[] response){
        HttpStatus status = (response.length == 0) ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(response, status);
    }
}
