package com.magent.config.auth;

public class UsbOauthClientCredentials {
    private String tokenServer;
    private String clientId;
    private String clientSecret;

    public UsbOauthClientCredentials(String tokenServer, String clientId, String clientSecret) {
        this.tokenServer = tokenServer;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getTokenServer() {
        return this.tokenServer;
    }

    public void setTokenServer(String tokenServer) {
        this.tokenServer = tokenServer;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return this.clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String toString() {
        return "UsbOauthClientCredentials{tokenServer=\'" + this.tokenServer + '\'' + ", clientId=\'" + this.clientId + '\'' + '}';
    }
}

