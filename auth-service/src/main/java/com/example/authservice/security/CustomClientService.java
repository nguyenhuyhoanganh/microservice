package com.example.authservice.security;

import com.example.authservice.entity.*;
import com.example.authservice.exception.ClientNotFoundException;
import com.example.authservice.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        boolean hasExist = clientRepository.findById(Long.valueOf(registeredClient.getId())).isPresent();
        if (hasExist == true)
            throw new RuntimeException("Client already exists");
        saveClient(registeredClient);
    }

    @Override
    public RegisteredClient findById(String id) {
        Client client = clientRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ClientNotFoundException("Client not found by id"));
        return Client.from(client);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Client client = clientRepository.findByClientId(clientId)
                .orElseThrow(() -> new ClientNotFoundException("Client not found by client_id"));
        // wtf find client can't fetch redirect_uri???
        Set<RedirectUri> redirectUris = redirectUriRepository.findByClient(client);
        client.setRedirectUris(
                redirectUris.stream()
                        .map(redirectUri -> RedirectUri.builder().uri(redirectUri.getUri()).build())
                        .collect(Collectors.toSet()));
        RegisteredClient registeredClient = Client.from(client);
        return registeredClient;
    }

    private void saveClient(RegisteredClient registeredClient) {
        // scopes
        Set<Scope> scopes = registeredClient.getScopes().stream().map(
                scope -> {
                    Optional<Scope> scopeOptional = scopeRepository.findByScopeName(scope);
                    if (scopeOptional.isEmpty())
                        throw new RuntimeException("Can't use other scopes except OPENID, PROFILE, EMAIL," +
                                " ADDRESS and PHONE");
                    return scopeOptional.get();
                }
        ).collect(Collectors.toSet());

        // authentication method
        Set<AuthenticationMethod> authenticationMethods = registeredClient.getClientAuthenticationMethods().stream().map(
                authenticationMethod -> {
                    Optional<AuthenticationMethod> authenticationMethodOptional =
                            authenticationMethodRepository.findByAuthenticationMethod(authenticationMethod.getValue());
                    if (authenticationMethodOptional.isEmpty())
                        throw new RuntimeException("Can't use other authentication method except CLIENT_SECRET_BASIC," +
                                " CLIENT_SECRET_POST, CLIENT_SECRET_JWT, PRIVATE_KEY_JWT, and NONE");
                    return authenticationMethodOptional.get();
                }
        ).collect(Collectors.toSet());

        // grant type
        Set<GrantType> grantTypes = registeredClient.getAuthorizationGrantTypes().stream().map(
                grantType -> {
                    Optional<GrantType> grantTypeOptional =
                            grantTypeRepository.findByGrantType(grantType.getValue());
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
//                .logoutRedirectUris(logoutRedirectUris)
                .scopes(scopes)
                .authenticationMethods(authenticationMethods)
                .grantTypes(grantTypes).build();

        // redirectUris
        Set<RedirectUri> redirectUris = registeredClient.getRedirectUris().stream().map(
                redirectUri -> redirectUriRepository.save(RedirectUri.builder()
                        .uri(redirectUri)
                        .client(client)
                        .build())
        ).collect(Collectors.toSet());

        clientRepository.save(client);
    }
}
