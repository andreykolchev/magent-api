package com.magent.controller;

import com.magent.controller.interfaces.GeneralController;
import com.magent.domain.TemporaryUser;
import com.magent.service.interfaces.SmsService;
import com.magent.service.interfaces.UserService;
import com.magent.service.interfaces.secureservice.OauthService;
import com.magent.utils.SecurityUtils;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.io.IOException;

@Api("Authorization controller")
@RestController
@RequestMapping("/")
public class LoginController implements GeneralController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    @Qualifier("tokenServices")
    ResourceServerTokenServices resourceServerTokenServices;
    @Autowired
    @Qualifier("oauthSimple")
    private OauthService oauthService;
    @Autowired
    @Qualifier("oauthOtp")
    private OauthService otpOauthService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private UserService userService;

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
                                                   @RequestParam(required = false) boolean withSms) throws IOException, NotFoundException {
        LOGGER.info("Loggin in " + username);
        if (!withSms) {
            OAuth2AccessToken token = oauthService.getToken(username, password);
            return new ResponseEntity(token, HttpStatus.OK);
        } else {
            if (userService.isPasswordCorrect(username, password)) {
                return getDefaultResponce(smsService.sendOtpForRegisteredUser(username).getEndPeriod().getTime(),HttpStatus.OK,HttpStatus.BAD_REQUEST);
            } else {
                throw new NotFoundException("password incorrect");
            }
        }
    }


    @ApiOperation(value = "Refresh access token by refresh token",
            httpMethod = "POST",
            produces = "application/json",
            response = OAuth2AccessToken.class)

    @RequestMapping(value = {"/refresh"}, method = {RequestMethod.POST})
    public ResponseEntity<OAuth2AccessToken> refresh(@ApiParam(value = "Refresh token", required = true)
                                                     @RequestParam(required = true) String refreshToken) {
        //  LOGGER.debug("Refreshing access token by refresh token = {}", refreshToken);
        OAuth2AccessToken accessToken = oauthService.refreshToken(refreshToken);
//        LOGGER.debug("Sent new access token = {} for refresh token = {}", accessToken.getValue(), refreshToken);
        return new ResponseEntity(accessToken, HttpStatus.OK);
    }

    @RequestMapping(value = "/login/confirmotp", method = RequestMethod.POST)
    public ResponseEntity<OAuth2AccessToken> confirmOtp(@RequestParam(required = true) String username,
                                                        @RequestParam(required = true) String password,
                                                        @RequestParam(required = true) String otpPass) {
        OAuth2AccessToken accessToken = otpOauthService.getToken(username, hashPass(password, otpPass));
        return new ResponseEntity(accessToken, HttpStatus.OK);
    }

    /**
     * @param username - present username from DB
     * @param password - hashed one time password
     * @return
     * @throws NotFoundException - if password not correct
     * @throws IOException       - if can't recent otp
     */
    @RequestMapping(value = "/login/recentotp", method = RequestMethod.POST)
    public ResponseEntity<String> recentOtpForTegisteredUser(@RequestParam String username,
                                                             @RequestParam String password) throws NotFoundException, IOException {
        if (userService.isPasswordCorrect(username, password)) {
            return getDefaultResponce(smsService.sendOtpForRegisteredUser(username).getEndPeriod().getTime(), HttpStatus.OK, HttpStatus.BAD_REQUEST);
        } else throw new NotFoundException("password not correct");
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<String> registerNewUser(@RequestBody TemporaryUser temporaryUser) throws ValidationException {
        return getDefaultResponce(userService.isNewUserSaved(temporaryUser).getEndPeriod().getTime(), HttpStatus.OK, HttpStatus.BAD_REQUEST);
    }

    /**
     * @param username - phone number (login)
     * @return - HttpStatus
     * @throws NotFoundException - if user not present in db
     */
    @RequestMapping(value = "/signup/recentotp", method = RequestMethod.POST)
    public ResponseEntity<String> recentOtpForUnregisteredUser(@RequestParam("username") String username) throws NotFoundException {
        return getDefaultResponce(smsService.recentConfirmation(username).getEndPeriod().getTime(), HttpStatus.OK, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/signup/registerconfirm", method = RequestMethod.POST)
    public ResponseEntity confirmRegistration(@RequestParam("username") String username,
                                              @RequestParam("otp") String otp) throws NotFoundException {

        ResponseEntity responseEntity = getDefaultResponceStatusOnly(userService.confirmRegistration(username, otp), HttpStatus.OK, HttpStatus.NOT_FOUND);
        smsService.sendSuccessfullRegistration(username);
        return responseEntity;
    }

    private String hashPass(String pass, String otpPass) {
        String hashPass = SecurityUtils.hashPassword(pass);
        String otpHashPass = SecurityUtils.hashPassword(otpPass);
        return (hashPass + otpHashPass);
    }
}

