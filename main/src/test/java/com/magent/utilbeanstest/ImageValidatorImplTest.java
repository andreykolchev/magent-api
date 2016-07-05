package com.magent.utilbeanstest;

import com.magent.config.ServiceConfig;
import com.magent.utils.validators.ImageValidator;
import com.magent.utils.validators.ImageValidatorImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by artomov.ihor on 22.06.2016.
 */
public class ImageValidatorImplTest extends ServiceConfig {
    @Autowired
    private ImageValidator imageValidator;

    @Test
    public void checkSizePositivePngTest() throws IOException {
        byte[] imageBody = Files.readAllBytes(Paths.get(URI.create(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("testimages/testimage.png")))));
        Assert.assertTrue(imageValidator.isSizeCorrect(imageBody, "png"));

    }

    @Test
    public void checkSizeNegativePngTest() throws IOException {
        byte[] imageBody = Files.readAllBytes(Paths.get(URI.create(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("testimages/testimagenegative.png")))));
        Assert.assertFalse(imageValidator.isSizeCorrect(imageBody, "png"));
    }

    @Test
    public void checkSizePositiveSvgTest() throws IOException {
        byte[] imageBody = Files.readAllBytes(Paths.get(URI.create(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("testimages/positiveSvg.svg")))));
        Assert.assertTrue(imageValidator.isSizeCorrect(imageBody, "svg"));
    }

    @Test
    public void checkSizeNegativeSvgTest() throws IOException {
        byte[] imageBody = Files.readAllBytes(Paths.get(URI.create(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("testimages/svgimage.svg")))));
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