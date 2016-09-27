package com.magent.controller;

import com.magent.authmodule.service.interfaces.OauthService;
import com.magent.authmodule.utils.validators.UserValidatorImpl;
import com.magent.controller.interfaces.GeneralController;
import com.magent.domain.TemporaryUser;
import com.magent.servicemodule.service.interfaces.SmsService;
import com.magent.servicemodule.service.interfaces.UserService;
import com.magent.utils.SecurityUtils;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.text.ParseException;

/**
 * Controller for all logic which related to authentication
 */
@Api("Authorization controller")
@RestController
@RequestMapping("/")
public class LoginController implements GeneralController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    @Qualifier("oauthSimple")
    private OauthService oauthService;

    @Autowired
    @Qualifier("oauthOtp")
    private OauthService otpOauthService;

    @Autowired
    @Qualifier("smsServiceImpl")
    private SmsService smsService;

    @Autowired
    @Qualifier("smsDemoServiceImpl")
    private SmsService demoSmsService;

    @Value("${sms.send.real}")
    private boolean isSmsGateActive;

    @Autowired
    private UserService userService;

    /**
     * @param username user login
     * @param password hashed one time user password
     * @param withSms  flag to logic how authenticate user with otp(true value) or just with login and password(false value) . If true service just send Otp number to user
     *                 after that he should confirm from /login/confirmotp api endpoint
     * @return token from oauth server which indicated in property file, or generated otp number if isSmsGateActive is false else returned end sms period as String
     * @throws IOException                              if SmsService can't connect to sms gateway
     * @throws NotFoundException                        if userService.isPasswordCorrect method return false, than system decided params not valid
     * @throws UserValidatorImpl.UserIsBlockedException when maximum wrong entries equals attempt.quantity value in property file
     * @throws ParseException                           when SmsServiceImpl bean can't get end period of sms
     * @see SmsService
     */
    @ApiOperation(
            value = "Login",
            httpMethod = "POST",
            produces = "application/json",
            response = OAuth2AccessToken.class
    )
    @RequestMapping(value = {"/login"}, method = {RequestMethod.POST})
    public ResponseEntity<OAuth2AccessToken> login(@ApiParam(value = "User name", required = true)
                                                   @RequestParam(required = true) String username,
                                                   @ApiParam(value = "Hashed password", required = true)
                                                   @RequestParam(required = true) String password,
                                                   @RequestParam(required = false) boolean withSms) throws IOException, NotFoundException, UserValidatorImpl.UserIsBlockedException, ParseException {
        LOGGER.info("Loggin in " + username);
        SmsService sender = isSmsGateActive ? smsService : demoSmsService;
        if (!withSms) {
            OAuth2AccessToken token = oauthService.getToken(username, password);
            return new ResponseEntity(token, HttpStatus.OK);
        } else {
            if (userService.isPasswordCorrect(username, password)) {
                return getDefaultResponce(sender.sendOtpForRegisteredUser(username), HttpStatus.OK, HttpStatus.BAD_REQUEST);
            } else {
                throw new NotFoundException("password incorrect");
            }
        }
    }

    /**
     * @param refreshToken from OAuth2AccessToken value refresh_token
     * @param otp          flag to show how user gets current token, if user was authenticated with otp he must get new token with flag
     * @return token from oauth server which indicated in property file
     */
    @ApiOperation(value = "Refresh access token by refresh token",
            httpMethod = "POST",
            produces = "application/json",
            response = OAuth2AccessToken.class)

    @RequestMapping(value = {"/refresh"}, method = {RequestMethod.POST})
    public ResponseEntity<OAuth2AccessToken> refresh(@ApiParam(value = "Refresh token", required = true)
                                                     @RequestParam(required = true) String refreshToken,
                                                     @RequestParam(required = false) boolean otp) {
        if (!otp) {
            return new ResponseEntity(oauthService.refreshToken(refreshToken), HttpStatus.OK);
        } else return new ResponseEntity(otpOauthService.refreshToken(refreshToken), HttpStatus.OK);
    }

    /**
     * method confirmed authentication with otp numbers from sms
     *
     * @param username user login
     * @param password user password (one time hashed)
     * @param otpPass  otp number(one time hashed)
     * @return token from oauth server which indicated in property file if login and password and otp are valid
     * @throws UserValidatorImpl.UserIsBlockedException when maximum wrong entries equals attempt.quantity value in property file
     */
    @RequestMapping(value = "/login/confirmotp", method = RequestMethod.POST)
    public ResponseEntity<OAuth2AccessToken> confirmOtp(@RequestParam(required = true) String username,
                                                        @RequestParam(required = true) String password,
                                                        @RequestParam(required = true) String otpPass) throws UserValidatorImpl.UserIsBlockedException {
        OAuth2AccessToken accessToken = otpOauthService.getToken(username, hashPass(password, otpPass));
        return new ResponseEntity(accessToken, HttpStatus.OK);
    }

    /**
     * @param username present username from DB
     * @param password hashed one time password
     * @return generated otp number if isSmsGateActive is false else returned end sms period as String
     * @throws NotFoundException if password not correct or user login
     * @throws IOException       if can't recent otp
     * @see com.magent.domain.enums.TimeIntervalConstants (OTP_INTERVAL_NAME)
     */
    @RequestMapping(value = "/login/resendotp", method = RequestMethod.POST)
    public ResponseEntity<String> recentOtpForTegisteredUser(@RequestParam String username,
                                                             @RequestParam String password) throws NotFoundException, IOException, UserValidatorImpl.UserIsBlockedException, ParseException {
        SmsService sender = isSmsGateActive ? smsService : demoSmsService;
        if (userService.isPasswordCorrect(username, password)) {
            return getDefaultResponce(sender.sendOtpForRegisteredUser(username), HttpStatus.OK, HttpStatus.BAD_REQUEST);
        } else throw new NotFoundException("password not correct");
    }

    /**
     * @param temporaryUser user which want to regiter
     * @return generated otp number if isSmsGateActive is false else returned end sms period as String
     * @throws ValidationException if some of param tmpUser not valid
     * @throws ParseException      if can't get end period of sms
     * @see com.magent.servicemodule.utils.validators.interfaces.GeneralValidator
     */
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<String> registerNewUser(@RequestBody TemporaryUser temporaryUser) throws ValidationException, ParseException {
        return getDefaultResponce(userService.isNewUserSaved(temporaryUser), HttpStatus.OK, HttpStatus.BAD_REQUEST);
    }

    /**
     * @param username phone number (login)
     * @return generated otp number if isSmsGateActive is false else returned end sms period as String
     * @throws NotFoundException if user not present in db
     * @throws ParseException if can't get end period of sms
     */
    @RequestMapping(value = "/signup/resendotp", method = RequestMethod.POST)
    public ResponseEntity<String> recentOtpForUnregisteredUser(@RequestParam("username") String username) throws NotFoundException, ParseException {
        SmsService sender = isSmsGateActive ? smsService : demoSmsService;
        return getDefaultResponce(sender.recentConfirmation(username), HttpStatus.OK, HttpStatus.NOT_FOUND);
    }

    /**
     * method created user if user not registered yet and returned HttpStatus.OK if it created.
     *
     * @param username TemporaryUser login
     * @param otp      numbers from sms
     * @return returned HttpStatus.OK if it created
     * @throws NotFoundException if UserService can't find current TemporaryUser
     */
    @RequestMapping(value = "/signup/registerconfirm", method = RequestMethod.POST)
    public ResponseEntity confirmRegistration(@RequestParam("username") String username,
                                              @RequestParam("otp") String otp) throws NotFoundException {

        ResponseEntity responseEntity = getDefaultResponceStatusOnly(userService.confirmRegistration(username, otp), HttpStatus.OK, HttpStatus.NOT_FOUND);
        SmsService sender = isSmsGateActive ? smsService : demoSmsService;
        sender.sendSuccessfullRegistration(username);
        return responseEntity;
    }

    /**
     *  methods allowed to change password for user
     * @param username User login
     * @return generated otp number if isSmsGateActive is false, else returned end sms period as String
     * @throws ValidationException if user tried change password more than maxAttemptQuantity value from property file
     * @throws ParseException if can't get end period of sms
     */
    @RequestMapping(value = "/login/changePassword", method = RequestMethod.POST)
    public ResponseEntity<String> sendOtpForForgotPassword(@RequestParam("username") String username) throws ValidationException, ParseException {
        SmsService sender = isSmsGateActive ? smsService : demoSmsService;
        //send sms
        return getDefaultResponce(sender.sendForgotPassword(username), HttpStatus.OK, HttpStatus.NOT_FOUND);
    }

    /**
     *
     * @param userName login
     * @param password new password
     * @param otp numbers from sms for current user
     * @return HttpStatus.OK if password is changed
     * @throws ValidationException if otp not valid or password doesn't match requirements
     * @throws UserValidatorImpl.UserIsBlockedException if user is blocked
     */
    @RequestMapping(value = "login/changePasswordConfirm", method = RequestMethod.POST)
    public ResponseEntity confirmChangePassword(@RequestParam("username") String userName,
                                                @RequestParam("password") String password,
                                                @RequestParam("otp") String otp) throws ValidationException, UserValidatorImpl.UserIsBlockedException {

        return getDefaultResponceStatusOnly(userService.changePassword(userName, password, otp), HttpStatus.OK, HttpStatus.BAD_REQUEST);

    }

    /**
     * @param pass    - user password
     * @param otpPass - numbers from send sms
     * @return hashed one time password and otp password which concatenated together
     * @see com.magent.utils.SecurityUtils class
     */
    private String hashPass(String pass, String otpPass) {
        String hashPass = SecurityUtils.hashPassword(pass);
        String otpHashPass = SecurityUtils.hashPassword(otpPass);
        return (hashPass + otpHashPass);
    }
}

