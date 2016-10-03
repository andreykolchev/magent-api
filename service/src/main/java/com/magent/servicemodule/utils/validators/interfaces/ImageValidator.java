package com.magent.servicemodule.utils.validators.interfaces;

import com.magent.servicemodule.utils.validators.ImageValidatorImpl;

import javax.xml.bind.ValidationException;
import java.io.IOException;

/**
 * Created by artomov.ihor on 22.06.2016.
 */
public interface ImageValidator {
    /**
     * check correct size.
     * for svg format see open source TwelveMonkeys plugin https://github.com/haraldk/TwelveMonkeys
     *
     * @param imageBody
     * @param formatName
     * @return is size of image correct
     * @throws IOException
     * @throws ValidationException
     */
    boolean isSizeCorrect(byte[] imageBody, String formatName) throws IOException, ValidationException;

    /**
     * Allowed only svg and png extensions
     *
     * @param fileName
     * @return is format of image correct
     * @throws ImageValidatorImpl.NotCorrectImageExtension
     */
    String getImageFormat(String fileName) throws ImageValidatorImpl.NotCorrectImageExtension;
}
