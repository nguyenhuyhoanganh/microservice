package com.example.notificationservice.controller;

import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
public class NotificationController {

//    private final OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;  // proxy
//
//    public NotificationController(OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager) {
//        this.oAuth2AuthorizedClientManager = oAuth2AuthorizedClientManager;
//    }
//
//    @GetMapping("/notify")
//    public void inform() {
//        OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest
//                .withClientRegistrationId("1")
//                .principal("client")
//                .build();
//
//        // request to the auth-service
//        OAuth2AuthorizedClient client = oAuth2AuthorizedClientManager.authorize(request);
//
//        log.info(client.getAccessToken().getTokenValue());
//        //added on the Authorization header on the request "Bearer ..."
//    }

    @GetMapping("/notify")
    public void inform() {
        log.info(LocalDateTime.now().toString());
    }
}
