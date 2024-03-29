package com.example.authservice.enumeration;

public enum TokenFormatEnum {
    // SELF_CONTAINED (default): uses JWT, can read and decode information in the payload
    // without calling back to auth-server.
    SELF_CONTAINED("self-contained"),

    // REFERENCE: uses opaque-token (an indecipherable string, cannot read information).
    // Use opaque-token to call back to auth-server via "/oauth2/introspect" to get information.
    REFERENCE("reference");

    private final String value;

    TokenFormatEnum(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public static TokenFormatEnum fromValue(String value) {
        for (TokenFormatEnum tokenFormatEnum : TokenFormatEnum.values()) {
            if (tokenFormatEnum.value.equals(value)) {
                return tokenFormatEnum;
            }
        }
        throw new IllegalArgumentException("No token_format_enum has the value: " + value);
    }
}
