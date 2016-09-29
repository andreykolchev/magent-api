package com.magent.authmodule.service.impl;

import com.magent.authmodule.service.interfaces.OauthService;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * General realization templates for OauthService implementation
 */
abstract class OauthServiceAbstract implements OauthService {

    protected OAuth2AccessToken getTokenForTemplate(String login, String pass, OAuth2RestTemplate template) {
        OAuth2AccessToken token;
        ResourceOwnerPasswordAccessTokenProvider provider = new ResourceOwnerPasswordAccessTokenProvider();
        ResourceOwnerPasswordResourceDetails resourceDetails = (ResourceOwnerPasswordResourceDetails) template.getResource();
        resourceDetails.setUsername(login);
        resourceDetails.setPassword(pass);
        //We have to use provider because oauthRestTemplate.getAccessToken() creates session
        // and use it in order to get new or refresh access token
        token = provider.obtainAccessToken(resourceDetails, new DefaultAccessTokenRequest());
        return token;
    }

    protected OAuth2AccessToken getRefreshTokenByTemplate(String refreshToken, OAuth2RestTemplate template) {
        OAuth2AccessToken accessToken;
        ResourceOwnerPasswordAccessTokenProvider e = new ResourceOwnerPasswordAccessTokenProvider();
        BaseOAuth2ProtectedResourceDetails resourceDetails = (BaseOAuth2ProtectedResourceDetails) template.getResource();
        DefaultOAuth2RefreshToken token = new DefaultOAuth2RefreshToken(refreshToken);
        accessToken = e.refreshAccessToken(resourceDetails, token, new DefaultAccessTokenRequest());
        return accessToken;
    }
}
