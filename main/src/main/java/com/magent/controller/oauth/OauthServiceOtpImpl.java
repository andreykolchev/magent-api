package com.magent.controller.oauth;

import com.magent.service.interfaces.secureservice.OauthService;
import com.magent.utils.validators.UserValidatorImpl;
import com.magent.utils.validators.interfaces.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

/**
 * Created  on 14.06.2016.
 */
@Service(value = "oauthOtp")
public class OauthServiceOtpImpl implements OauthService {
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
        OAuth2AccessToken accessToken;
        ResourceOwnerPasswordAccessTokenProvider e = new ResourceOwnerPasswordAccessTokenProvider();
        BaseOAuth2ProtectedResourceDetails resourceDetails = (BaseOAuth2ProtectedResourceDetails) this.otpOauthRestTemplate.getResource();
        DefaultOAuth2RefreshToken token = new DefaultOAuth2RefreshToken(refreshToken);
        accessToken = e.refreshAccessToken(resourceDetails, token, new DefaultAccessTokenRequest());
        return accessToken;
    }
}
