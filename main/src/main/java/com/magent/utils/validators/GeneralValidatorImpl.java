package com.magent.utils.validators;

import com.magent.utils.validators.interfaces.GeneralValidator;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * Created on 07.07.2016.
 */
@Component
public class GeneralValidatorImpl implements GeneralValidator {

    @Override
    public boolean isEmailValid(String email) {
        return Pattern.matches("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b",email);
    }

    @Override
    public boolean isPhoneValid(String phoneNumber) {
        return Pattern.matches("^\\+[1-9]{1}[0-9]{3,14}$", phoneNumber);
    }

    @Override
    public boolean isNameCorrect(String name) {
        return Pattern.matches("^[A-Z]{1}[a-z]{1,255}$", name);
    }

    @Override
    public boolean isPasswordValid(String pwd) {
        return Pattern.matches("((?=.*[a-z])(?=.*[A-Z]|.*\\d|.*[@#$%]).{6,20})", pwd);
    }

}
