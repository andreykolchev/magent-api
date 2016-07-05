package com.magent.service.interfaces.secureservice;

import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

public interface OauthService {
    /**
     * @param login unique login for user
     * @param pass  -password for user details , depends on realization (can contains only password or mix password and otp )
     * @return
     * @see - module ds_authServer
     */
    OAuth2AccessToken getToken(String login, String pass);

    OAuth2AccessToken refreshToken(String refreshToken);

    default OAuth2AccessToken getTokenForTemplate(String login, String pass, OAuth2RestTemplate template) {
        OAuth2AccessToken token = null;
        ResourceOwnerPasswordAccessTokenProvider provider = new ResourceOwnerPasswordAccessTokenProvider();
        ResourceOwnerPasswordResourceDetails resourceDetails = (ResourceOwnerPasswordResourceDetails) template.getResource();
        resourceDetails.setUsername(login);
        resourceDetails.setPassword(pass);
        //We have to use provider because usbOauthRestTemplate.getAccessToken() creates session
        // and use it in order to get new or refresh access token
        token = provider.obtainAccessToken(resourceDetails, new DefaultAccessTokenRequest());
        return token;
    }
}
