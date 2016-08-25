package com.magent.utils.validators.interfaces;

import com.magent.utils.validators.UserValidatorImpl;

/**
 * Created on 01.08.2016.
 */
public interface UserValidator {

    /**
     * method checks for flag isBlocked in UserPersonal.class
     * @param login - user login
     * @throws UserValidatorImpl.UserIsBlockedException if isBlocked flag in true state
     */
    void checkForBlock(String login) throws UserValidatorImpl.UserIsBlockedException;

    /**
     * method adds one increment in wrongEntersEntering variable
     * if authorization server throws OAuth2AccessDeniedException exception.
     * And method adds expiry date, when the block is considered ended.
     * And method always in new transaction.
     * @param login - user login
     */
    void addOneWrongEnter(String login);
}
