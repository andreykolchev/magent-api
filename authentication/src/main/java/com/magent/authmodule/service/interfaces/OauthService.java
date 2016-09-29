package com.magent.authmodule.service.interfaces;

import com.magent.authmodule.utils.validators.UserValidatorImpl;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * General interface for get access token and refresh token.
 * see OauthServiceAbstract class
 */
public interface OauthService {
    /**
     * @param login unique login for user
     * @param pass  password for user details , depends on realization (can contains only password or mix password and otp )
     * @return token from oauth server
     * @throws UserValidatorImpl.UserIsBlockedException when user is blocked
     * @see com.magent.authmodule.utils.validators.interfaces.UserValidator
     * see - module ds_authServer
     */
    OAuth2AccessToken getToken(String login, String pass) throws UserValidatorImpl.UserIsBlockedException;

    /**
     * important refresh token works for same service which got token, so user must gets token from the same service which get access token
     *
     * @param refreshToken refresh token from getLogin method
     * @return token from oauth server
     */
    OAuth2AccessToken refreshToken(String refreshToken);

}
