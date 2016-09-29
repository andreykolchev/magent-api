package com.magent.authmodule.config;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.util.Assert;

/**
 *
 *
 */
@Configuration
@EnableOAuth2Client
public class OAuthClientConfig implements InitializingBean {

    @Autowired(required = false)
    @Qualifier("clientCredentials")
    private OauthClientCredentials clientCredentials;

    @Autowired(required = false)
    @Qualifier("clientCredentialsOtp")
    private OauthClientCredentials clientCredentialsOtp;

    public OAuthClientConfig() {
    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.clientCredentials, "Named bean clientCredentials of type OauthClientCredentials not found in context");
    }

    @Bean
    public ResourceOwnerPasswordResourceDetails oauthResourceDetails() {
        return createDetails(clientCredentials);
    }

    @Bean
    public ResourceOwnerPasswordResourceDetails oauthOtpResourceDetails() {
        return createDetails(clientCredentialsOtp);
    }

    /**
     * template for register with otp
     */
    @Bean
    public OAuth2RestTemplate otpOauthRestTemplate(OAuth2ClientContext clientContext) {
        return new OAuth2RestTemplate(this.oauthOtpResourceDetails(), clientContext);
    }

    //template for register with otp
    @Bean
    public OAuth2RestTemplate oauthRestTemplate(OAuth2ClientContext clientContext) {
        return new OAuth2RestTemplate(this.oauthResourceDetails(), clientContext);
    }

    private final class MagentResourceOwnerPasswordResourceDetails extends ResourceOwnerPasswordResourceDetails {
        private MagentResourceOwnerPasswordResourceDetails() {
        }

        public boolean isClientOnly() {
            return true;
        }
    }

    //not bean
    private MagentResourceOwnerPasswordResourceDetails createDetails(OauthClientCredentials credentials) {
        MagentResourceOwnerPasswordResourceDetails details = new MagentResourceOwnerPasswordResourceDetails();
        details.setId(credentials.getClientId());
        details.setClientId(credentials.getClientId());
        details.setClientSecret(credentials.getClientSecret());
        details.setAccessTokenUri(credentials.getTokenServer() + "/oauth/token");
        details.setGrantType("password");
        details.setAuthenticationScheme(AuthenticationScheme.form);
        return details;
    }
}
