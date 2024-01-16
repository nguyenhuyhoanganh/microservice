package com.example.authservice.enumeration;

public enum TokenFormatEnum {
    SELF_CONTAINED("self-contained"),
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
