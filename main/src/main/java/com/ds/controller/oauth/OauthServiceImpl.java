package com.ds.controller.oauth;

import com.ds.service.interfaces.secureservice.OauthService;
import com.ds.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

/**
 * Created by artomov.ihor on 14.06.2016.
 */
@Service(value = "oauthSimple")
public class OauthServiceImpl implements OauthService {

    @Autowired
    @Qualifier("usbOauthRestTemplate")
    private OAuth2RestTemplate usbOauthRestTemplate;

    @Override
    public OAuth2AccessToken getToken(String login, String pass) {
        //password hashing
        pass = SecurityUtils.hashPassword(pass);
        return getTokenForTemplate(login,pass,usbOauthRestTemplate);
    }

    @Override
    public OAuth2AccessToken refreshToken(String refreshToken) {
        OAuth2AccessToken accessToken;
        ResourceOwnerPasswordAccessTokenProvider e = new ResourceOwnerPasswordAccessTokenProvider();
        BaseOAuth2ProtectedResourceDetails resourceDetails = (BaseOAuth2ProtectedResourceDetails) this.usbOauthRestTemplate.getResource();
        DefaultOAuth2RefreshToken token = new DefaultOAuth2RefreshToken(refreshToken);
        accessToken = e.refreshAccessToken(resourceDetails, token, new DefaultAccessTokenRequest());
        return accessToken;

    }
}
