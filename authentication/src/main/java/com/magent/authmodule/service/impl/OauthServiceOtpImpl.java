package com.magent.authmodule.service.impl;

import com.magent.authmodule.utils.validators.UserValidatorImpl;
import com.magent.authmodule.utils.validators.interfaces.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

/**
 * Created  on 14.06.2016.
 */
@Service(value = "oauthOtp")
public class OauthServiceOtpImpl extends OauthServiceAbstract {
    @Autowired
    @Qualifier("otpOauthRestTemplate")
    private OAuth2RestTemplate otpOauthRestTemplate;

    @Autowired
    private UserValidator userValidator;

    @Override
    public OAuth2AccessToken getToken(String login, String pass) throws UserValidatorImpl.UserIsBlockedException {
        userValidator.checkForBlock(login);
        try {
            return getTokenForTemplate(login, pass, otpOauthRestTemplate);
        } catch (OAuth2AccessDeniedException e) {
            //add one wrong enter
            userValidator.addOneWrongEnter(login);
            throw new OAuth2AccessDeniedException("wrong enter for login " + login);
        }
    }

    @Override
    public OAuth2AccessToken refreshToken(String refreshToken) {
       return getRefreshTokenByTemplate(refreshToken,this.otpOauthRestTemplate);
    }
}
