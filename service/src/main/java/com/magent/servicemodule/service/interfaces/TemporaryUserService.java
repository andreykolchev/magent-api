package com.magent.servicemodule.service.interfaces;

import com.magent.domain.TemporaryUser;

/**
 * Created on 26.09.2016.
 */
public interface TemporaryUserService {
    TemporaryUser getByLogin(String login);

    TemporaryUser getByLoginAndOtp(String login, String otp);

    TemporaryUser save(TemporaryUser temporaryUser);

}
