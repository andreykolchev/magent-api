package com.magent.servicemodule.service.impl;

import com.magent.domain.TemporaryUser;
import com.magent.repository.TemporaryUserRepository;
import com.magent.servicemodule.service.interfaces.TemporaryUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created on 26.09.2016.
 */
@Service
@Transactional(readOnly = true)
class TemporaryUserServiceImpl implements TemporaryUserService {
    @Autowired
    private TemporaryUserRepository temporaryUserRepository;

    @Override
    public TemporaryUser getByLogin(String login) {
        return temporaryUserRepository.getByLogin(login);
    }

    @Override
    public TemporaryUser getByLoginAndOtp(String login, String otp) {
        return temporaryUserRepository.getByLoginAndOtp(login, otp);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TemporaryUser save(TemporaryUser temporaryUser) {
        return temporaryUserRepository.save(temporaryUser);
    }
}
