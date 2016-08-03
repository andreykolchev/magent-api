package com.magent.utils.validators;

import com.magent.domain.UserPersonal;
import com.magent.repository.UserPersonalRepository;
import com.magent.utils.dateutils.DateUtils;
import com.magent.utils.validators.interfaces.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private DateUtils dateUtils;

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
        if (userPersonal.getWrongEntersEntering()<3){
            int wrongEntries=userPersonal.getWrongEntersEntering();
            userPersonal.setWrongEntersEntering(++wrongEntries);
            if (userPersonal.getWrongEntersEntering()>=3){
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
