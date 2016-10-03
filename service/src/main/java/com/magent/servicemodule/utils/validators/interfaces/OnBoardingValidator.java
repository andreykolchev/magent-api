package com.magent.servicemodule.utils.validators.interfaces;

import com.magent.domain.OnBoarding;
import com.magent.servicemodule.utils.validators.ImageValidatorImpl;

import javax.xml.bind.ValidationException;
import java.io.IOException;

/**
 * Created by artomov.ihor on 22.06.2016.
 */
public interface OnBoardingValidator {
    /**
     * @param onBoarding
     * @return is OnBoard entity correct
     * @throws ImageValidatorImpl.NotCorrectImageExtension
     * @throws IOException
     * @throws ValidationException
     * @see OnBoarding
     */
    boolean isOnBoardEntityValid(OnBoarding onBoarding) throws ImageValidatorImpl.NotCorrectImageExtension, IOException, ValidationException;
}
