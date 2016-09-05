package com.magent.servicemodule.utils.validators;

import com.magent.servicemodule.utils.validators.interfaces.ImageValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.xml.bind.ValidationException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by artomov.ihor on 22.06.2016.
 */
@Component
public class ImageValidatorImpl implements ImageValidator {
    @Value("${json.max.size}")
    private int maxFileSize;

    @Override
    public boolean isSizeCorrect(byte[] imageBody, String formatName) throws IOException, ValidationException {
        if (imageBody.length > (maxFileSize * 1000000)) throw new ValidationException("image should be not less than 2 mb");
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
        return (height == 230 && width == 230);
    }

    @Override
    public String getImageFormat(String fileName) throws NotCorrectImageExtension {
        String[] tmp = fileName.split("\\.");
        if (tmp.length != 2) throw new NotCorrectImageExtension("file name doesn't have extension");
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
