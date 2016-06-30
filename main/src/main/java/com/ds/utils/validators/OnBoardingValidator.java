package com.ds.utils.validators;

import com.ds.domain.OnBoarding;

import java.io.IOException;

/**
 * Created by artomov.ihor on 22.06.2016.
 */
public interface OnBoardingValidator {
    boolean isOnBoardEntityValid(OnBoarding onBoarding) throws ImageValidatorImpl.NotCorrectImageExtension, IOException;
}
