package com.magent.service.interfaces;

import com.magent.domain.TemporaryUser;
import javassist.NotFoundException;

import javax.xml.bind.ValidationException;
import java.io.IOException;

/**
 * Created by artomov.ihor on 06.2016.
 */
public interface SmsService {
    void isConfirmationSended(String toPhone) throws IOException;
    TemporaryUser sendConfirmationAndSaveUser(TemporaryUser temporaryUser) throws ValidationException;
    TemporaryUser recentConfirmation(String login) throws NotFoundException;
    void sendSuccessfullRegistration(String login);
}
