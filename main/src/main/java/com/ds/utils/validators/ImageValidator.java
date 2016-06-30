package com.ds.utils.validators;

import java.io.IOException;

/**
 * Created by artomov.ihor on 22.06.2016.
 */
public interface ImageValidator {
    // check correct size. According to SAP_45 task, image size must be 170x170
    // for svg format see open source TwelveMonkeys plugin https://github.com/haraldk/TwelveMonkeys
    boolean isSizeCorrect(byte[] imageBody,String formatName) throws IOException;
    // According to SAP_45 task, allowed only svg and png formats
    String getImageFormat(String fileName) throws ImageValidatorImpl.NotCorrectImageExtension;
}
