package com.example.ecommerceservice.client.impl;

import com.example.ecommerceservice.client.INotificationServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationServiceClientImpl implements INotificationServiceClient {
    @Override
    public void inform() {
        log.error("Notification service has errors!");
        throw new RuntimeException("Notification service has errors!");
    }
}