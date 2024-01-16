package com.example.authservice.enumeration;

public enum GrantTypeEnum {
    // This grant type is suitable for confidential clients that can securely maintain a client secret.
    // The flow involves redirecting the user to the authorization server to obtain an authorization code,
    // and then exchanging that code for an access token.
    AUTHORIZATION_CODE("authorization_code"),

    // Used to obtain a new access token by presenting a valid refresh token.
    REFRESH_TOKEN("refresh_token"),

    // Suitable for confidential clients to obtain an access token based on client credentials (client ID and secret).
    CLIENT_CREDENTIALS("client_credentials"),

    // Allows a resource owner to provide their credentials (username and password) directly to the client,
    // which can then exchange them for an access token.
    PASSWORD("password"),

    // Allows the client to present a pre-issued JWT (JSON Web Token) as an assertion for obtaining an access token.
    JWT_BEARER("urn:ietf:params:oauth:grant-type:jwt-bearer"),

    // Designed for devices that may not have a direct input method for user credentials.
    // Involves displaying a code to the user, who then uses another device to authorize the access.
    DEVICE_CODE("urn:ietf:params:oauth:grant-type:device_code");

    private final String value;

    GrantTypeEnum(String value) {
        this.value = value;
    }

    public static GrantTypeEnum fromValue(String value) {
        for (GrantTypeEnum grantTypeEnum : GrantTypeEnum.values()) {
            if (grantTypeEnum.value.equals(value)) {
                return grantTypeEnum;
            }
        }
        throw new IllegalArgumentException("No grant_type_enum has the value: " + value);
    }

    public String getValue() {
        return value;
    }
}
