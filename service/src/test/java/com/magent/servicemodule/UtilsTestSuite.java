package com.magent.servicemodule;

import com.magent.servicemodule.utilbeantest.ComissionCalculatorImplTest;
import com.magent.servicemodule.utilbeantest.GeneralValidatorImplTest;
import com.magent.servicemodule.utilbeantest.ImageValidatorImplTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by artomov.ihor on 27.05.2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ComissionCalculatorImplTest.class,
        GeneralValidatorImplTest.class,
        ImageValidatorImplTest.class
})
public class UtilsTestSuite {
}
