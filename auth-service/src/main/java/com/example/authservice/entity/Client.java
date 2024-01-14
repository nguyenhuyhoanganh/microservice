package com.example.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", unique = true)
    private String clientId;

    private String clientName;

    private String secret;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private Set<RedirectUri> redirectUris;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private Set<RedirectUri> logoutRedirectUris;

    @ManyToMany
    @JoinTable(name = "clients_scopes", joinColumns = @JoinColumn(name = "client_id"), inverseJoinColumns =
    @JoinColumn(name = "scope_id"), uniqueConstraints = @UniqueConstraint(columnNames = {"client_id", "scope_id"}))
    private Set<Scope> scopes;

    @ManyToMany
    @JoinTable(name = "clients_authentication_methods", joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns =
            @JoinColumn(name = "authentication_method_id"), uniqueConstraints = @UniqueConstraint(columnNames = {"client_id", "authentication_method_id"}))
    private Set<AuthenticationMethod> authenticationMethods;

    @ManyToMany
    @JoinTable(name = "clients_grant_types", joinColumns = @JoinColumn(name = "client_id"), inverseJoinColumns =
    @JoinColumn(name = "grant_type_id"), uniqueConstraints = @UniqueConstraint(columnNames = {"client_id", "grant_type_id"}))
    private Set<GrantType> grantTypes;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "issued_at", nullable = false, updatable = false)
    private Date issuedAt;

    private Long expiredTime = 1000 * 60 * 30L;

    // token_settings, client_settings

    public static RegisteredClient from(Client client) {
        return RegisteredClient.withId(String.valueOf(client.getId()))
                .clientId(client.getClientId())
                .clientSecret(client.getSecret())
                .clientName(client.getClientName())
                .scopes(scopes -> client.getScopes().stream().map(scope -> scope.getScopeName().name()))
                .redirectUris(redirectUris -> client.getRedirectUris().stream().map(redirectUri -> redirectUri.getUri()))
                .postLogoutRedirectUris(redirectUris -> client.getLogoutRedirectUris().stream()
                        .map(redirectUri -> redirectUri.getUri()))
                .clientAuthenticationMethods(
                        authenticationMethods -> client.getAuthenticationMethods().stream()
                                .map(authenticationMethod ->
                                        new ClientAuthenticationMethod(authenticationMethod.getAuthenticationMethod().name())))
                .authorizationGrantTypes(
                        grantTypes -> client.getGrantTypes().stream()
                                .map(grantType -> new AuthorizationGrantType(grantType.getGrantType().name())))
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .tokenSettings(TokenSettings.builder()
//                .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                        .accessTokenTimeToLive(Duration.ofMillis(client.getExpiredTime())).build())
                .build();
    }
}
