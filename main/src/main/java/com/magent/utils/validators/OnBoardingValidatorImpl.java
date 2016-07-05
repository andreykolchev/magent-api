package com.magent.utils.validators;

import com.magent.domain.OnBoarding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by artomov.ihor on 22.06.2016.
 */
@Component
public class OnBoardingValidatorImpl implements OnBoardingValidator {
    @Autowired
    private ImageValidator imageValidator;

    @Override
    public boolean isOnBoardEntityValid(OnBoarding onBoarding) throws ImageValidatorImpl.NotCorrectImageExtension, IOException {
        return (imageValidator
                .isSizeCorrect
                        (onBoarding.getImage(), imageValidator.getImageFormat(onBoarding.getFullFileName())) &&
                isLenghtCorrect(onBoarding));
    }

    private boolean isLenghtCorrect(OnBoarding onBoarding) {
        if (Objects.nonNull(onBoarding.getContent()) && Objects.nonNull(onBoarding.getDescription()))
            return (onBoarding.getContent().length() < 51 &&
                    onBoarding.getDescription().length() < 251);

        if (Objects.nonNull(onBoarding.getDescription()) && Objects.isNull(onBoarding.getContent()))
            return onBoarding.getDescription().length() < 251;

        if (Objects.isNull(onBoarding.getDescription()) && Objects.nonNull(onBoarding.getContent()))
            return onBoarding.getContent().length() < 51;

        else if (Objects.isNull(onBoarding.getContent()) && Objects.isNull(onBoarding.getDescription()))
            return true;

        return false;
    }

    public static class InvalidOnboardEntity extends Exception {
        public InvalidOnboardEntity(String message) {
            super(message);
        }
    }
}