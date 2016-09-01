package com.magent.authmodule.utils.validators;

import com.magent.authmodule.utils.validators.interfaces.UserValidator;
import com.magent.domain.UserPersonal;
import com.magent.repository.UserPersonalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created on 01.08.2016.
 */
@Component
public class UserValidatorImpl implements UserValidator {

    @Autowired
    private UserPersonalRepository userPersonalRepository;

    @Value("${wrong.enters}")
    private int wrongEnters;

    @Override
    @Transactional(readOnly = true)
    public void checkForBlock(String login) throws UserIsBlockedException {
        UserPersonal userPersonal=userPersonalRepository.getByLogin(login);
        if (userPersonal.isBlocked())throw new UserIsBlockedException("user is blocked try again after 2 minutes");
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void addOneWrongEnter(String login) {
        UserPersonal userPersonal=userPersonalRepository.getByLogin(login);
        if (userPersonal.getWrongEntersEntering()< wrongEnters){
            int wrongEntries=userPersonal.getWrongEntersEntering();
            userPersonal.setWrongEntersEntering(++wrongEntries);
            if (userPersonal.getWrongEntersEntering()>= wrongEnters){
                userPersonal.setBlocked(true);
                userPersonal.setBlockExpired(new Date());
                userPersonalRepository.save(userPersonal);
            }
        }
    }

    public class UserIsBlockedException extends Exception {
        public UserIsBlockedException(String message) {
            super(message);
        }
    }
}
