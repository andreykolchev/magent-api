package com.magent;

import com.magent.servicemodule.utilbeanstest.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created on 27.05.2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(value = {
        TransactionsXlsReaderImplTest.class,
        TransactionsXlsWriterImplTest.class,
        CsvReaderImplTest.class,
        BalanceUploaderXlsWriterImplTest.class
})
public class ReportUtilsTestSuite {
}
