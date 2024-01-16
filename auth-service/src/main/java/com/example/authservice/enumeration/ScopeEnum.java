package com.example.authservice.enumeration;

public enum ScopeEnum {
    OPENID("openid"),
    PROFILE("profile"),
    EMAIL("email"),
    ADDRESS("address"),
    PHONE("phone");

    private final String value;

    ScopeEnum(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public static ScopeEnum fromValue(String value) {
        for (ScopeEnum scopeEnum : ScopeEnum.values()) {
            if (scopeEnum.value.equals(value)) {
                return scopeEnum;
            }
        }
        throw new IllegalArgumentException("No scope_enum has the value: " + value);
    }
}
