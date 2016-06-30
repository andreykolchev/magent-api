package com.ds.utils.validators;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by artomov.ihor on 22.06.2016.
 */
@Component
public class ImageValidatorImpl implements ImageValidator {
    @Override
    public boolean isSizeCorrect(byte[] imageBody, String formatName) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(imageBody);
        ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
        ImageReader reader = ImageIO.getImageReadersByFormatName(formatName).next();
        reader.setInput(imageInputStream);

        BufferedImage image = reader.read(0);

        inputStream.close();
        imageInputStream.close();

        //get height and width
        int height = image.getHeight();
        int width = image.getWidth();
        return (height == 170 && width == 170);
    }

    @Override
    public String getImageFormat(String fileName) throws NotCorrectImageExtension {
        String[] tmp = fileName.split("\\.");
        if (tmp.length!=2)throw new NotCorrectImageExtension("file name doesn't have extension");
        if (isFormatCorrect(tmp[1])) return tmp[1];
        else throw new NotCorrectImageExtension("Not correct image extension. Supports only svg and png extensions");
    }

    private boolean isFormatCorrect(String fileExtension) {
        return fileExtension.equalsIgnoreCase("png") || fileExtension.equalsIgnoreCase("svg");
    }

    public static class NotCorrectImageExtension extends Exception {
        public NotCorrectImageExtension(String message) {
            super(message);
        }
    }
}
