package com.example.authservice.enumeration;

public enum GrantTypeEnum {
    AUTHORIZATION_CODE("authorization_code"),
    REFRESH_TOKEN("refresh_token"),
    CLIENT_CREDENTIALS("client_credentials"),
    PASSWORD("password"),
    JWT_BEARER("urn:ietf:params:oauth:grant-type:jwt-bearer"),
    DEVICE_CODE("urn:ietf:params:oauth:grant-type:device_code");

    private final String value;

    GrantTypeEnum(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public static GrantTypeEnum fromValue(String value) {
        for (GrantTypeEnum grantTypeEnum : GrantTypeEnum.values()) {
            if (grantTypeEnum.value.equals(value)) {
                return grantTypeEnum;
            }
        }
        throw new IllegalArgumentException("No grant_type_enum has the value: " + value);
    }
}
