package com.example.authservice.enumeration;

public enum ScopeEnum {
    // This scope indicates that the client is making an OpenID Connect authentication request,
    // and the authorization server should return an ID Token as part of the response.
    OPENID("openid"),

    // This scope requests access to the user's profile information,
    // which may include basic details such as name, family name, given name, etc.
    PROFILE("profile"),

    // This scope requests access to the user's email address.
    EMAIL("email"),

    // This scope requests access to the user's postal address information.
    ADDRESS("address"),

    // This scope requests access to the user's phone number.
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
