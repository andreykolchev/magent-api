package com.magent.utilbeanstest;

import com.magent.config.ServiceConfig;
import com.magent.servicemodule.utils.validators.interfaces.GeneralValidator;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * GeneralValidatorImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>???. 8, 2016</pre>
 */
public class GeneralValidatorImplTest extends ServiceConfig {
    @Autowired
    private GeneralValidator generalValidator;


    /**
     * Method: isEmailValid(String email)
     */
    @Test
    public void testIsEmailValidPossitive() throws Exception {
        Assert.assertTrue(generalValidator.isEmailValid("test@gmail.com"));
        Assert.assertTrue(generalValidator.isEmailValid("test123@gmail.com"));
        Assert.assertTrue(generalValidator.isEmailValid("test123@mail.ru"));
        Assert.assertTrue(generalValidator.isEmailValid("test123@rambler.ru"));
        Assert.assertTrue(generalValidator.isEmailValid("test@yahoo.com"));
        Assert.assertTrue(generalValidator.isEmailValid("Test123@meta.ua"));
        Assert.assertTrue(generalValidator.isEmailValid("tester@roots.com.ua"));
        Assert.assertTrue(generalValidator.isEmailValid("tester.t.t@rootsasdasd.com.ua"));

    }

    @Test
    public void testIsEmailValidNegative() throws Exception {
        Assert.assertFalse(generalValidator.isEmailValid("test!gmail.com"));
        Assert.assertFalse(generalValidator.isEmailValid("test@gmail"));
        Assert.assertFalse(generalValidator.isEmailValid("@gmail.com"));
    }

    /**
     * Method: isPhoneValid(String phoneNumber)
     */
    @Test
    public void testIsPhoneValidPositive() throws Exception {
        Assert.assertTrue(generalValidator.isPhoneValid("+380971231234"));
        Assert.assertTrue(generalValidator.isPhoneValid("+480971231234"));
        Assert.assertTrue(generalValidator.isPhoneValid("+780971231234"));
    }

    @Test
    public void testIsPhoneValidNegative() throws Exception {
        Assert.assertFalse(generalValidator.isPhoneValid("380971231234"));
        Assert.assertFalse(generalValidator.isPhoneValid("+7(809)71231234"));
        Assert.assertFalse(generalValidator.isPhoneValid("+7(809)71231234"));
        Assert.assertFalse(generalValidator.isPhoneValid("+38(097)123-1234"));
    }

    /**
     * Method: isNameCorrect(String name)
     * min length 2 char
     */
    @Test
    public void testIsNameCorrect() throws Exception {
        Assert.assertTrue(generalValidator.isNameCorrect("Vasya"));
        Assert.assertTrue(generalValidator.isNameCorrect("Petya"));
        Assert.assertTrue(generalValidator.isNameCorrect("Testov"));
        Assert.assertTrue(generalValidator.isNameCorrect("Testovich"));
        Assert.assertFalse(generalValidator.isNameCorrect("Vasya1"));
        Assert.assertFalse(generalValidator.isNameCorrect("Vasya@"));
        Assert.assertFalse(generalValidator.isNameCorrect("vasya"));
    }

    /**
     * Method: isPasswordValid(String pwd)
     * requirements 6 characters 1 of them with big letter or number or spec symbol
     */
    @Test
    public void testIsPasswordValid() throws Exception {
        Assert.assertTrue(generalValidator.isPasswordValid("vasyAa"));
        Assert.assertTrue(generalValidator.isPasswordValid("Tester"));
        Assert.assertTrue(generalValidator.isPasswordValid("heroku1"));
        Assert.assertTrue(generalValidator.isPasswordValid("herok1"));
        Assert.assertTrue(generalValidator.isPasswordValid("1penshit"));

        Assert.assertFalse(generalValidator.isPasswordValid("sesto@12"));
        Assert.assertFalse(generalValidator.isPasswordValid("@Wsesto12"));
        Assert.assertFalse(generalValidator.isPasswordValid("sesto@"));
        Assert.assertFalse(generalValidator.isPasswordValid("testov"));
        Assert.assertFalse(generalValidator.isPasswordValid("Testo"));
        Assert.assertFalse(generalValidator.isPasswordValid("!estos"));
        Assert.assertFalse(generalValidator.isPasswordValid("vasya"));
        Assert.assertFalse(generalValidator.isPasswordValid("openshi%"));
        Assert.assertFalse(generalValidator.isPasswordValid("lambda"));
        Assert.assertFalse(generalValidator.isPasswordValid("Lambd"));
        Assert.assertFalse(generalValidator.isPasswordValid("Vasy@"));
        Assert.assertFalse(generalValidator.isPasswordValid("Vasya."));
        Assert.assertFalse(generalValidator.isPasswordValid("test12#"));
        Assert.assertFalse(generalValidator.isPasswordValid("test12."));
        Assert.assertFalse(generalValidator.isPasswordValid("test12&"));
        Assert.assertFalse(generalValidator.isPasswordValid("test12?"));
        Assert.assertFalse(generalValidator.isPasswordValid("test12-"));


    }

} 
