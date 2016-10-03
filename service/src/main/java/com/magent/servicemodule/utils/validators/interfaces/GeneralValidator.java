package com.magent.servicemodule.utils.validators.interfaces;

/**
 * Created by artomov.ihor on 07.07.2016.
 */
public interface GeneralValidator {
    /**
     * @param email
     * @return is email valid
     */
    boolean isEmailValid(String email);

    /**
     * @param phoneNumber
     * @return is phoneNumber valid
     */
    boolean isPhoneValid(String phoneNumber);

    /**
     * @param name
     * @return is name valid
     */
    boolean isNameCorrect(String name);

    /**
     * @param pwd
     * @return is password valid
     */
    boolean isPasswordValid(String pwd);
}
