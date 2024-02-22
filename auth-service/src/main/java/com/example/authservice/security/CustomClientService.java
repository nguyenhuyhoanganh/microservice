package com.example.authservice.security;

import com.example.authservice.entity.*;
import com.example.authservice.enumeration.AuthenticationMethodEnum;
import com.example.authservice.enumeration.GrantTypeEnum;
import com.example.authservice.enumeration.ScopeEnum;
import com.example.authservice.exception.ClientNotFoundException;
import com.example.authservice.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CustomClientService implements RegisteredClientRepository {

    private final ClientRepository clientRepository;
    private final AuthenticationMethodRepository authenticationMethodRepository;
    private final ScopeRepository scopeRepository;
    private final RedirectUriRepository redirectUriRepository;
    private final GrantTypeRepository grantTypeRepository;

    @Override
    public void save(RegisteredClient registeredClient) {
        log.info("save " + registeredClient);
        boolean hasExist = clientRepository.findById(Long.valueOf(registeredClient.getId())).isPresent();
        if (hasExist == true)
            throw new RuntimeException("Client already exists");
        saveClient(registeredClient);
    }

    @Override
    public RegisteredClient findById(String id) {
        Client client = clientRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ClientNotFoundException("Client not found by id"));
        RegisteredClient registeredClient = Client.from(client);
//        log.info("findById " + id + " " + registeredClient);
        return registeredClient;
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Client client = clientRepository.findByClientId(clientId)
                .orElseThrow(() -> new ClientNotFoundException("Client not found by client_id"));
        RegisteredClient registeredClient = Client.from(client);
//        log.info("findByClientId " + clientId + " " + registeredClient);
        return registeredClient;
    }

    private void saveClient(RegisteredClient registeredClient) {
        // scope
        Set<Scope> scopes = registeredClient.getScopes().stream().map(
                scope -> {
                    ScopeEnum scopeEnum = ScopeEnum.fromValue(scope);
                    Optional<Scope> scopeOptional = scopeRepository.findByScopeName(scopeEnum.name());
                    if (scopeOptional.isEmpty())
                        throw new RuntimeException("Can't use other scopes except OPENID, PROFILE, EMAIL," +
                                " ADDRESS and PHONE");
                    return scopeOptional.get();
                }
        ).collect(Collectors.toSet());

        // authentication_method
        Set<AuthenticationMethod> authenticationMethods = registeredClient.getClientAuthenticationMethods().stream().map(
                authenticationMethod -> {
                    AuthenticationMethodEnum authenticationMethodEnum =
                            AuthenticationMethodEnum.fromValue(authenticationMethod.getValue());
                    Optional<AuthenticationMethod> authenticationMethodOptional =
                            authenticationMethodRepository.findByAuthenticationMethod(authenticationMethodEnum.name());
                    if (authenticationMethodOptional.isEmpty())
                        throw new RuntimeException("Can't use other authentication method except CLIENT_SECRET_BASIC," +
                                " CLIENT_SECRET_POST, CLIENT_SECRET_JWT, PRIVATE_KEY_JWT, and NONE");
                    return authenticationMethodOptional.get();
                }
        ).collect(Collectors.toSet());

        // grant_type
        Set<GrantType> grantTypes = registeredClient.getAuthorizationGrantTypes().stream().map(
                grantType -> {
                    GrantTypeEnum grantTypeEnum = GrantTypeEnum.fromValue(grantType.getValue());
                    Optional<GrantType> grantTypeOptional =
                            grantTypeRepository.findByGrantType(grantTypeEnum.name());
                    if (grantTypeOptional.isEmpty())
                        throw new RuntimeException("Can't use other grant type except AUTHORIZATION_CODE, " +
                                "REFRESH_TOKEN, CLIENT_CREDENTIALS, PASSWORD, JWT_BEARER and DEVICE_CODE");
                    return grantTypeOptional.get();
                }
        ).collect(Collectors.toSet());

        // client
        Client client = Client.builder()
                .clientId(registeredClient.getClientId())
                .secret(registeredClient.getClientSecret())
                .clientName(registeredClient.getClientName())
                .scopes(scopes)
                .authenticationMethods(authenticationMethods)
                .grantTypes(grantTypes).build();

        // save client
        Client clientCreated = clientRepository.save(client);

        // save redirect_uri
        registeredClient.getRedirectUris().stream().forEach(
                redirectUri -> redirectUriRepository.save(RedirectUri.builder()
                        .uri(redirectUri)
                        .client(clientCreated)
                        .isLogoutUri(false)
                        .build())
        );

        // save logout_redirect_uri
        registeredClient.getPostLogoutRedirectUris().stream().forEach(
                redirectUri -> redirectUriRepository.save(RedirectUri.builder()
                        .uri(redirectUri)
                        .client(clientCreated)
                        .isLogoutUri(true)
                        .build())
        );
    }
}
