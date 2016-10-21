package com.magent.servicemodule.utilbeantest;

import com.magent.servicemodule.config.ServiceModuleServiceConfig;
import com.magent.servicemodule.utils.validators.ImageValidatorImpl;
import com.magent.servicemodule.utils.validators.interfaces.ImageValidator;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by artomov.ihor on 22.06.2016.
 */
public class ImageValidatorImplTest extends ServiceModuleServiceConfig {
    @Autowired
    private ImageValidator imageValidator;
    @Value("${resource.path}")
    private String resourcePath;

    @Test
    public void checkSizePositivePngTest() throws IOException, ValidationException {

        byte[] imageBody = Files.readAllBytes(Paths.get((resourcePath+"testimages/testimage.png")));
        Assert.assertTrue(imageValidator.isSizeCorrect(imageBody, "png"));

    }

    @Test
    public void checkSizeNegativePngTest() throws IOException, ValidationException {
        byte[] imageBody = Files.readAllBytes(Paths.get((resourcePath+"testimages/testimagenegative.png")));
        Assert.assertFalse(imageValidator.isSizeCorrect(imageBody, "png"));
    }

    @Test
    public void checkSizePositiveSvgTest() throws IOException, ValidationException {
        byte[] imageBody = Files.readAllBytes(Paths.get(resourcePath+"testimages/positiveSvg.svg"));
        Assert.assertTrue(imageValidator.isSizeCorrect(imageBody, "svg"));
    }

    @Test
    public void checkSizeNegativeSvgTest() throws IOException, ValidationException {
        byte[] imageBody = Files.readAllBytes(Paths.get(resourcePath+"testimages/svgimage.svg"));
        Assert.assertFalse(imageValidator.isSizeCorrect(imageBody, "svg"));
    }

    @Test
    public void testExctensionValidatorPossitive() throws ImageValidatorImpl.NotCorrectImageExtension {
        String correctFileNameSvg = "test.svg";
        String correctFileNamePng = "test.png";
        Assert.assertEquals(imageValidator.getImageFormat(correctFileNamePng),"png");
        Assert.assertEquals(imageValidator.getImageFormat(correctFileNameSvg),"svg");
    }

    @Test(expected = ImageValidatorImpl.NotCorrectImageExtension.class)
    public void testExctensionValidatorNegative() throws ImageValidatorImpl.NotCorrectImageExtension {
        String notCorrectFormat = "test.jpg";
        imageValidator.getImageFormat(notCorrectFormat);
    }

    @Test(expected = ImageValidatorImpl.NotCorrectImageExtension.class)
    public void testExctensionValidatorNegativeWithoutExctension() throws ImageValidatorImpl.NotCorrectImageExtension {
        String notCorrectFormat = "test";
        imageValidator.getImageFormat(notCorrectFormat);
    }
}
