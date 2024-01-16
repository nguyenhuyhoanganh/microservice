package com.example.authservice.enumeration;

// Specified method to send client_id and secret when authorization_code has been received.
public enum AuthenticationMethodEnum {
    // Add header Authorization : "Basic " + base64.encode(username=`client_id`&password=`secret`).
    // Use Postman: enable `Authorization` > chose `Basic Auth` > fill in `client_id` information in Username and
    // `secret` in Password.
    CLIENT_SECRET_BASIC("client_secret_basic"),

    // Add `client_id` and `secret` through the body of the POST method
    CLIENT_SECRET_POST("client_secret_post"),

    // Use JWT (one key)
    CLIENT_SECRET_JWT("client_secret_jwt"),

    // Use JWT with key-pair(public key and private key)
    PRIVATE_KEY_JWT("private_key_jwt"),

    // Don't provide `client_id` and `secret` to the auth-server
    NONE("none");

    private final String value;

    AuthenticationMethodEnum(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public static AuthenticationMethodEnum fromValue(String value) {
        for (AuthenticationMethodEnum authenticationMethodEnum : AuthenticationMethodEnum.values()) {
            if (authenticationMethodEnum.value.equals(value)) {
                return authenticationMethodEnum;
            }
        }
        throw new IllegalArgumentException("No authentication_method_enum has the value: " + value);
    }
}
