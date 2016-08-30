package com.magent.reportmodule.utils.xlsutil.interfaces;

import com.magent.domain.User;

import java.io.IOException;
import java.util.List;

/**
 * Created by artomov.ihor on 30.05.2016.
 */
public interface BalanceUploaderXlsWriter extends XlsCreator {
    /**
     *
     * @param userList for creating report
     * @return xlsfile as multipartFile
     */
    public byte[] createXlsReport(List<User> userList) throws IOException;

}
