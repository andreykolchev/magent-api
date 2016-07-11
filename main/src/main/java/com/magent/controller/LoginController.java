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
//        LOGGER.debug("Loggin in " + username);
        if (!withSms) {
            OAuth2AccessToken token = oauthService.getToken(username, password);
            return new ResponseEntity(token, HttpStatus.OK);
        } else {
            if (userService.isPasswordCorrect(username, password)) {
                smsService.sendOtpForRegisteredUser(username);
                return new ResponseEntity(HttpStatus.OK);
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
     * @param login    - present login from DB
     * @param password - hashed one time password
     * @return
     * @throws NotFoundException - if not present in db
     * @throws IOException       - if can't recent otp
     */
    @RequestMapping(value = "/login/recentotp", method = RequestMethod.POST)
    public ResponseEntity recentOtpForTegisteredUser(@RequestParam String login,
                                                     @RequestParam String password) throws NotFoundException, IOException {
        if (userService.isPasswordCorrect(login, password)) {
            smsService.sendOtpForRegisteredUser(login);
            return new ResponseEntity(HttpStatus.OK);
        } else throw new NotFoundException("password not correct");
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<String> registerNewUser(@RequestBody TemporaryUser temporaryUser) throws ValidationException {
        return getDefaultResponce(userService.isNewUserSaved(temporaryUser).getEndPeriod().getTime(), HttpStatus.OK, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/signup/recentotp", method = RequestMethod.POST)
    public ResponseEntity recentOtpForUnregisteredUser(@RequestParam("login") String login) throws NotFoundException {
        return getDefaultResponceStatusOnly(smsService.recentConfirmation(login), HttpStatus.OK, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/signup/registerconfirm", method = RequestMethod.POST)
    public ResponseEntity confirmRegistration(@RequestParam("login") String login,
                                              @RequestParam("otp") String otp) throws NotFoundException {

        ResponseEntity responseEntity = getDefaultResponceStatusOnly(userService.confirmRegistration(login, otp), HttpStatus.OK, HttpStatus.NOT_FOUND);
        smsService.sendSuccessfullRegistration(login);
        return responseEntity;
    }

    private String hashPass(String pass, String otpPass) {
        String hashPass = SecurityUtils.hashPassword(pass);
        String otpHashPass = SecurityUtils.hashPassword(otpPass);
        return (hashPass + otpHashPass);
    }
}

