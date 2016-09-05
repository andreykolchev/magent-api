package com.magent.servicemodule.service.interfaces;

import com.magent.domain.SmsPassword;
import com.magent.domain.TemporaryUser;
import javassist.NotFoundException;

import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created on 06.2016.
 */
public interface SmsService {
    String sendOtpForRegisteredUser(String toPhone) throws IOException, ParseException;

    Object sendConfirmationAndSaveUser(TemporaryUser temporaryUser) throws ValidationException, ParseException;

    String recentConfirmation(String login) throws NotFoundException, ParseException;

    default void sendSuccessfullRegistration(String login) {
        //do nothing see implementation
    }

    default List<SmsPassword> getOldSmsPass(String sqlDate, String timeFromConfig) {
        return null;
    }

    default String getEndSmsPeriod() throws ParseException {
        //do nothing see implementation
        return null;
    }

    String sendForgotPassword(String toPhone) throws ValidationException, ParseException;
}
