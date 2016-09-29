package com.magent.authmodule.service.impl;


import com.magent.authmodule.utils.SecurityUtils;
import com.magent.authmodule.utils.validators.UserValidatorImpl;
import com.magent.authmodule.utils.validators.interfaces.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

/**
 * OauthService implementation for get token without otp password
 * Created  on 14.06.2016.
 */
@Service(value = "oauthSimple")
public class OauthServiceImpl extends OauthServiceAbstract {

    @Autowired
    @Qualifier("oauthRestTemplate")
    private OAuth2RestTemplate oauthRestTemplate;

    @Autowired
    @Qualifier("userValidatorImpl")
    private UserValidator userValidator;

    @Override
    public OAuth2AccessToken getToken(String login, String pass) throws UserValidatorImpl.UserIsBlockedException {
        //checks for block
        userValidator.checkForBlock(login);
        //password hashing
        pass = SecurityUtils.hashPassword(pass);
        try {
            return getTokenForTemplate(login, pass, oauthRestTemplate);
        } catch (OAuth2AccessDeniedException e) {
            //add one wrong enter
            userValidator.addOneWrongEnter(login);
            throw new OAuth2AccessDeniedException("wrong enter for login " + login);
        }
    }

    @Override
    public OAuth2AccessToken refreshToken(String refreshToken) {
        return getRefreshTokenByTemplate(refreshToken, this.oauthRestTemplate);
    }
}
