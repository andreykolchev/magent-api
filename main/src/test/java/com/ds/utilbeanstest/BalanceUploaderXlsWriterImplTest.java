package com.ds.utilbeanstest;

import com.ds.config.ServiceConfig;
import com.ds.domain.User;
import com.ds.service.interfaces.UserService;
import com.ds.utils.xlsutil.interfaces.BalanceUploaderXlsWriter;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.List;

/**
 * BalanceUploaderXlsWriterImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>????. 30, 2016</pre>
 */
public class BalanceUploaderXlsWriterImplTest extends ServiceConfig {

    @Value("${tmp.excel.file}")
    private String uploadPath;

    @Autowired
    private UserService userService;

    @Autowired
    private BalanceUploaderXlsWriter balanceUploaderXlsWriter;

    /**
     * Method: createXlsReportBalance(List<User> userList)
     */
    @Test
    public void testCreateXlsReport() throws Exception {
        List<User>userList=userService.getUsersForBalanceReport();
        byte[] res = balanceUploaderXlsWriter.createXlsReport(userList);
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(uploadPath + "balanceReport.xls")));
        stream.write(res);
        stream.close();
        File newXlsFile = new File(uploadPath + "balanceReport.xls");
        Assert.assertTrue(newXlsFile.canRead());
        Files.delete(newXlsFile.toPath());

    }

} 
