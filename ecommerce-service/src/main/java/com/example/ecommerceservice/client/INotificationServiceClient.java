package com.example.ecommerceservice.client;

import com.example.ecommerceservice.client.impl.NotificationServiceClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "notification-service", fallback = NotificationServiceClientImpl.class)
public interface INotificationServiceClient {
    @GetMapping("/notify")
    void inform(@RequestHeader("Authorization") String authorizationHeader);
}
