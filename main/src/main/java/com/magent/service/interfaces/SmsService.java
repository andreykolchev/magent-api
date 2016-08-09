package com.magent.service.interfaces;

import com.magent.domain.SmsPassword;
import com.magent.domain.TemporaryUser;
import javassist.NotFoundException;

import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by artomov.ihor on 06.2016.
 */
public interface SmsService {
    SmsPassword sendOtpForRegisteredUser(String toPhone) throws IOException;
    TemporaryUser sendConfirmationAndSaveUser(TemporaryUser temporaryUser) throws ValidationException;
    TemporaryUser recentConfirmation(String login) throws NotFoundException;
    void sendSuccessfullRegistration(String login);
    List<SmsPassword> getOldSmsPass(String sqlDate,String timeFromConfig);
    String getEndSmsPeriod() throws ParseException;
}
