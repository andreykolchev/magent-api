package com.magent.utils.validators;

import com.magent.domain.OnBoarding;

import javax.xml.bind.ValidationException;
import java.io.IOException;

/**
 * Created by artomov.ihor on 22.06.2016.
 */
public interface OnBoardingValidator {
    boolean isOnBoardEntityValid(OnBoarding onBoarding) throws ImageValidatorImpl.NotCorrectImageExtension, IOException, ValidationException;
}
