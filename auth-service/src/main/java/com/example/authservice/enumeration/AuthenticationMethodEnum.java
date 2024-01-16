package com.example.authservice.enumeration;

public enum AuthenticationMethodEnum {
    CLIENT_SECRET_BASIC("client_secret_basic"),
    CLIENT_SECRET_POST("client_secret_post"),
    CLIENT_SECRET_JWT("client_secret_jwt"),
    PRIVATE_KEY_JWT("private_key_jwt"),
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
