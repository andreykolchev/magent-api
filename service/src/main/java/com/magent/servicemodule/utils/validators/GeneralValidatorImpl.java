package com.magent.servicemodule.utils.validators;

import com.magent.servicemodule.utils.validators.interfaces.GeneralValidator;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * Created on 07.07.2016.
 */
@Component
class GeneralValidatorImpl implements GeneralValidator {

    /**
     * check the email by current pattern
     *
     * @param email
     * @return is email valid
     */
    @Override
    public boolean isEmailValid(String email) {
        return Pattern.matches("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b", email);
    }

    /**
     * check the phoneNumber by current pattern
     *
     * @param phoneNumber
     * @return is phoneNumber valid
     */
    @Override
    public boolean isPhoneValid(String phoneNumber) {
        return Pattern.matches("^\\+[1-9]{1}[0-9]{3,14}$", phoneNumber);
    }

    /**
     * check the name by current pattern
     *
     * @param name
     * @return is name valid
     */
    @Override
    public boolean isNameCorrect(String name) {
        return Pattern.matches("^[A-Z]{1}[a-z]{1,255}$", name);
    }

    /**
     * check the password by current pattern
     *
     * @param pwd
     * @return is password valid
     */
    @Override
    public boolean isPasswordValid(String pwd) {
        return Pattern.matches("((?=.*[a-z])(?=.*[A-Z]|.*\\d)(?!.*[\\W]).{6,20})", pwd);
    }

}
