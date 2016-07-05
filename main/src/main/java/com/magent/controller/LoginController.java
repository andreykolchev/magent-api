package com.magent.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Api("Authorization controller")
@RestController
@RequestMapping("/")
public class LoginController {
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
                smsService.isConfirmationSended(username);
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

    @RequestMapping(value = "/login/otp", method = RequestMethod.POST)
    public ResponseEntity<OAuth2AccessToken> confirmOtp(@RequestParam(required = true) String username,
                                                        @RequestParam(required = true) String password,
                                                        @RequestParam(required = true) String otpPass) {
        OAuth2AccessToken accessToken = otpOauthService.getToken(username, hashPass(password, otpPass));
        return new ResponseEntity(accessToken, HttpStatus.OK);
    }

    private String hashPass(String pass, String otpPass) {
        String hashPass = SecurityUtils.hashPassword(pass);
        String otpHashPass = SecurityUtils.hashPassword(otpPass);
        return (hashPass + otpHashPass);
    }
}

