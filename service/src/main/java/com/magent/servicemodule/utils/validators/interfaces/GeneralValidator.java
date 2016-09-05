package com.magent.servicemodule.utils.validators.interfaces;

/**
 * Created by artomov.ihor on 07.07.2016.
 */
public interface GeneralValidator {
    boolean isEmailValid(String email);
    boolean isPhoneValid(String phoneNumber);
    boolean isNameCorrect(String name);
    boolean isPasswordValid(String pwd);
}
