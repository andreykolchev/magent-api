package com.magent;

import com.magent.utilbeanstest.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by artomov.ihor on 27.05.2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ComissionCalculatorImplTest.class,
        TransactionsXlsReaderImplTest.class,
        TransactionsXlsWriterImplTest.class,
        CsvReaderImplTest.class,
        BalanceUploaderXlsWriterImplTest.class,
        GeneralValidatorImplTest.class

})
public class UtilsTestSuite {
}
