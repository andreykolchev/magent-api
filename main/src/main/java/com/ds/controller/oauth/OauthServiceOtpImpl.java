package com.ds.controller.oauth;

import com.ds.service.interfaces.secureservice.OauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

/**
 * Created by artomov.ihor on 14.06.2016.
 */
@Service(value = "oauthOtp")
public class OauthServiceOtpImpl implements OauthService {
    @Autowired
    @Qualifier("otpOauthRestTemplate")
    private OAuth2RestTemplate otpOauthRestTemplate;
    @Override
    public OAuth2AccessToken getToken(String login, String pass) {
        return getTokenForTemplate(login,pass,otpOauthRestTemplate);
    }

    @Override
    public OAuth2AccessToken refreshToken(String refreshToken) {
        return null;
    }
}
