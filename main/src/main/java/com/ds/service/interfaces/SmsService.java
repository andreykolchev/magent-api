package com.ds.service.interfaces;

import java.io.IOException;

/**
 * Created by artomov.ihor on 06.2016.
 */
public interface SmsService {
    void isConfirmationSended(String toPhone) throws IOException;
}
