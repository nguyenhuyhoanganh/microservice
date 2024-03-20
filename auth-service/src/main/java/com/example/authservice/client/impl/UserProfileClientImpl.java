package com.example.authservice.client.impl;

import com.example.authservice.client.IUserProfileClient;
import com.example.authservice.dto.ResponseDTO;
import com.example.authservice.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

@Slf4j
public class UserProfileClientImpl implements IUserProfileClient {
    @Override
    public ResponseEntity<ResponseDTO<UserDTO>> getByUsername(String username) {
        log.error("UserProfile service has errors!");
        throw new RuntimeException("UserProfile service has errors!");
    }
}
