package com.example.authservice.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Collection;
import java.util.UUID;

@Configuration
public class SecurityConfig {

    /**
     * Generates an RSA key pair for cryptographic operations.
     *
     * @return The generated RSA key pair.
     * @throws IllegalStateException if an error occurs during key pair generation.
     */
    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            // Initialize the KeyPairGenerator with RSA algorithm and 2048-bit key size
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);

            // Generate the key pair
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            // Throw an exception if any error occurs during key pair generation
            throw new IllegalStateException("Error generating RSA key pair", ex);
        }
        return keyPair;
    }

    /**
     * Provides a JWKSource with a sample RSA key pair.
     * Oauth2 Server Stater will use it to generate JWT.
     *
     * @return The JWKSource instance.
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        // Generate an RSA key pair
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        // Build an RSAKey with the public and private key
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();

        // Create a JWKSet with the RSAKey
        JWKSet jwkSet = new JWKSet(rsaKey);

        // Return an ImmutableJWKSet with the JWKSet
        return new ImmutableJWKSet<>(jwkSet);
    }

    /**
     * Provides a JwtDecoder using the JWKSource.
     *
     * @param jwkSource The JWKSource instance.
     * @return The JwtDecoder instance.
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        // Use OAuth2AuthorizationServerConfiguration to create a JwtDecoder
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    /**
     * Provides an instance of BCryptPasswordEncoder for password hashing.
     *
     * @return The BCryptPasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Return a new instance of BCryptPasswordEncoder
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the security settings for the Authorization Server.
     *
     * @param http The HttpSecurity instance.
     * @return The configured SecurityFilterChain for the Authorization Server.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        // Apply default security settings for OAuth2 Authorization Server
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        // Configure OIDC (OpenID Connect) customization
        // Retrieve the configuration of OAuth2AuthorizationServer
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                // OpenID Connect configuration uses default configuration
                // Customizer is a FunctionalInterface that receives an object of type T and customizes that object
                // ".withDefaults()" method returns the original object
                .oidc(Customizer.withDefaults());

        // Exception handling configuration
        http.exceptionHandling((exceptions) ->
                        // Redirect to the login page when not authenticated from the authorization endpoint
                        exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                )
                // OAuth2 Resource Server configuration
                // Accept access tokens for User Info and/or Client Registration
                .oauth2ResourceServer((resourceServer) -> resourceServer
                        .jwt(Customizer.withDefaults()));

        // Build and return the configured SecurityFilterChain
        return http.build();
    }

    /**
     * Configures the default security settings for other HTTP requests.
     *
     * @param http The HttpSecurity instance.
     * @return The configured SecurityFilterChain for other HTTP requests.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // Authorize all HTTP requests, requiring authentication
        http.authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                )
                // Form login handles the redirect to the login page from the authorization server filter chain
                .formLogin(Customizer.withDefaults());

        // Build and return the configured SecurityFilterChain
        return http.build();
    }

    /**
     * Add a client into in-memory use InMemoryRegisteredClientRepository.
     * (Used CustomClientService implement RegisteredClientRepository with annotation @Service instead)
     *
     * @return The RegisteredClientRepository instance.
     */
    /*
    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        // Create a sample OIDC client
        RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("client")
                .clientSecret("secret")
                // If you want to use OidcScopes, need to enable the OIDC configuration
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                // Don't specify "https://localhost" for redirect-uri because OIDC doesn't accept it
                // Here use a random uri, the purpose is just to get the authorization_code
                // on the uri when redirecting
                .redirectUri("https://springone.io/authorized")
                // CLIENT_SECRET_BASIC: Base64 encode and put the client-id and secret in the Authorization header,
                // with a prefix starting with "Basic "
                // Use postman to turn on Authorization, switch to Basic Auth type,
                // fill in client-id information in Username, secret in Password
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                // Customize access_token and id_token
                .tokenSettings(
                        TokenSettings.builder()
                                // REFERENCE: uses opaque-token (an indecipherable string, cannot read information).
                                // Use opaque-token to call back to auth-server via "/oauth2/introspect" to get
                                // information
                                // SELF_CONTAINED (default): uses JWT, can read and decode information in the payload
                                // without calling back to auth-server
//                                .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                                .accessTokenTimeToLive(Duration.ofSeconds(60 * 10))
                                .build()
                )
                .build();

        // Return an InMemoryRegisteredClientRepository with the sample OIDC client
        return new InMemoryRegisteredClientRepository(oidcClient);
    }
     */

    /**
     * Provides endpoint configurations according to the default implementation of oauth2
     * https://docs.spring.io/spring-authorization-server/reference/configuration-model.html#configuring-authorization-server-settings
     *
     * @return The AuthorizationServerSettings instance.
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        // Use default settings
        return AuthorizationServerSettings.builder().build();
    }

    /**
     * Customize JWT: claims, header, scope, ...
     *
     * @return The OAuth2TokenCustomizer instance.
     */
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> oAuth2TokenCustomizer() {
        return context -> {
            Collection<? extends GrantedAuthority> roles = context.getPrincipal().getAuthorities();
            // add roles to JWT claims
            context.getClaims().claim("roles", roles.stream().map(GrantedAuthority::getAuthority).toList());
        };
    }
}

/**
 * 1.Client uses the url pattern:
 * http://localhost:8000/oauth2/authorize?response_type=code&client_id=client&scope=openid&redirect_uri=https://springone.io/authorized&code_challenge=QYPAZ5NU8yvtlQ9erXrUYR-T5AGCjCF47vN-KsaI2A8&code_challenge_method=S256
 * to call the auth-server for authentication.
 * <p>
 * 2. Then auth-server redirects to the login page for the User to log in
 * <p>
 * 3. Successful login, auth-server redirects by redirect_uri (parameter passed on authentication request) like pattern:
 * https://springone.io/authorized?code=DHP01x-mvMhKwZHiUu9pMgrXxtaJ8KS5ufHJw4g7Mn7LK006CzelQE93u6EcKFZSM0xen8iEgOi5hmtNc8XShTbqW4nwhw7Xf2xZPssPPArAz2_mOxOZpyVToJh2yLFr
 * uri includes authorization_code
 * <p>
 * 4. Use authentication_code to call the path like pattern:
 * http://localhost:8000/oauth2/token?client_id=client&redirect_uri=https://springone.io/authorized&grant_type=authorization_code&code=DHP01x-mvMhKwZHiUu9pMgrXxtaJ8KS5ufHJw4g7Mn7LK006CzelQE93u6EcKFZSM0xen8iEgOi5hmtNc8XShTbqW4nwhw7Xf2xZPssPPArAz2_mOxOZpyVToJh2yLFr&code_verifier=qPsH306-ZDDaOE8DFzVn05TkN3ZZoVmI_
 * method: POST, Authorization: Basic Auth
 * fill in client-id information in Username, secret in Password
 * <p>
 * Note:
 * - By default, PCKE is used.
 * - The client creates a 'code_verifier', then uses the SHA-256 or SHA-512 hash function to generate the
 * 'code_challenge'
 * - The hash function to use is provided to the auth-server via the 'code_challenge_method' parameter in step 1 (used
 * SHA256 with value s256).
 * - After receiving the 'authorization_code' from the auth-server, the client uses it and sends it along with
 * the 'code_verifier' back to the auth-server. The purpose is for the auth-server to ensure that the correct user use
 * 'authorization_code' is the client holding the authentication request in step 1.
 * - 'authorization_code' can only be used once.
 * - The endpoints provided by the auth-server can be found by uri: http://localhost:8000/.well-known/openid-configuration
 * - Get the public-key using the uri: http://localhost:8000/oauth2/jwks
 * <p>
 * Note:
 * - By default, PCKE is used.
 * - The client creates a 'code_verifier', then uses the SHA-256 or SHA-512 hash function to generate the
 * 'code_challenge'
 * - The hash function to use is provided to the auth-server via the 'code_challenge_method' parameter in step 1 (used
 * SHA256 with value s256).
 * - After receiving the 'authorization_code' from the auth-server, the client uses it and sends it along with
 * the 'code_verifier' back to the auth-server. The purpose is for the auth-server to ensure that the correct user use
 * 'authorization_code' is the client holding the authentication request in step 1.
 * - 'authorization_code' can only be used once.
 * - The endpoints provided by the auth-server can be found by uri: http://localhost:8000/.well-known/openid-configuration
 * - Get the public-key using the uri: http://localhost:8000/oauth2/jwks
 */

/**
 * Note:
 * - By default, PCKE is used.
 * - The client creates a 'code_verifier', then uses the SHA-256 or SHA-512 hash function to generate the
 *   'code_challenge'
 * - The hash function to use is provided to the auth-server via the 'code_challenge_method' parameter in step 1 (used
 *   SHA256 with value s256).
 * - After receiving the 'authorization_code' from the auth-server, the client uses it and sends it along with
 *   the 'code_verifier' back to the auth-server. The purpose is for the auth-server to ensure that the correct user use
 *   'authorization_code' is the client holding the authentication request in step 1.
 * - 'authorization_code' can only be used once.
 * - The endpoints provided by the auth-server can be found by uri: http://localhost:8000/.well-known/openid-configuration
 * - Get the public-key using the uri: http://localhost:8000/oauth2/jwks
 */

