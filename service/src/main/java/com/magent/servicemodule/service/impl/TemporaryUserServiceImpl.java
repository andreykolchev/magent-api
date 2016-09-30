package com.magent.servicemodule.service.impl;

import com.magent.domain.TemporaryUser;
import com.magent.repository.TemporaryUserRepository;
import com.magent.servicemodule.service.interfaces.TemporaryUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * service for TemporaryUser operations
 */
@Service
@Transactional(readOnly = true)
class TemporaryUserServiceImpl implements TemporaryUserService {
    @Autowired
    private TemporaryUserRepository temporaryUserRepository;

    /**
     *
     * @param login
     * @return TemporaryUser
     */
    @Override
    public TemporaryUser getByLogin(String login) {
        return temporaryUserRepository.getByLogin(login);
    }

    /**
     *
     * @param login
     * @param otp
     * @return TemporaryUser
     */
    @Override
    public TemporaryUser getByLoginAndOtp(String login, String otp) {
        return temporaryUserRepository.getByLoginAndOtp(login, otp);
    }

    /**
     * Transactional method
     * @param temporaryUser TemporaryUser object for persist
     * @return TemporaryUser entity persisted in DB
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TemporaryUser save(TemporaryUser temporaryUser) {
        return temporaryUserRepository.save(temporaryUser);
    }
}
