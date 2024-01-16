package com.example.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "client")
    @SQLRestriction("is_logout_uri = false")
    private Collection<RedirectUri> redirectUris;

    @OneToMany(mappedBy = "client")
    @SQLRestriction("is_logout_uri = true")
    private Collection<RedirectUri> logoutRedirectUris;

    @ManyToMany
    @JoinTable(name = "clients_scopes", joinColumns = @JoinColumn(name = "client_id"), inverseJoinColumns =
    @JoinColumn(name = "scope_id"), uniqueConstraints = @UniqueConstraint(columnNames = {"client_id", "scope_id"}))
    private Collection<Scope> scopes;

    @ManyToMany
    @JoinTable(name = "clients_authentication_methods", joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns =
            @JoinColumn(name = "authentication_method_id"), uniqueConstraints = @UniqueConstraint(columnNames = {"client_id", "authentication_method_id"}))
    private Set<AuthenticationMethod> authenticationMethods;

    @ManyToMany
    @JoinTable(name = "clients_grant_types", joinColumns = @JoinColumn(name = "client_id"), inverseJoinColumns =
    @JoinColumn(name = "grant_type_id"), uniqueConstraints = @UniqueConstraint(columnNames = {"client_id", "grant_type_id"}))
    private Collection<GrantType> grantTypes;

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
                .scopes(scopes -> scopes.addAll(client.getScopes()
                        .stream()
                        .map(scope -> scope.getScopeName().getValue())
                        .collect(Collectors.toSet())))
                .redirectUris(redirectUris -> redirectUris.addAll(client.getRedirectUris()
                        .stream()
                        .map(redirectUri -> redirectUri.getUri())
                        .collect(Collectors.toSet())))
                .postLogoutRedirectUris(logoutUris -> logoutUris.addAll(client.getLogoutRedirectUris()
                        .stream()
                        .map(logoutUri -> logoutUri.getUri())
                        .collect(Collectors.toSet())))
                .clientAuthenticationMethods(
                        authenticationMethods -> authenticationMethods.addAll(client.getAuthenticationMethods()
                                .stream()
                                .map(authenticationMethod ->
                                        new ClientAuthenticationMethod(
                                                authenticationMethod.getAuthenticationMethod().getValue()))
                                .collect(Collectors.toSet())))
                .authorizationGrantTypes(
                        grantTypes -> grantTypes.addAll(client.getGrantTypes()
                                .stream()
                                .map(grantType ->
                                        new AuthorizationGrantType(grantType.getGrantType().getValue()))
                                .collect(Collectors.toSet())))
                .tokenSettings(TokenSettings.builder()
                        // use default jwt
                        .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                        .accessTokenTimeToLive(Duration.ofMillis(client.getExpiredTime())).build())
                .build();
    }
}
