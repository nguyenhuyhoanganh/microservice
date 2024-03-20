package com.example.authservice.client;

import com.example.authservice.client.impl.UserProfileClientImpl;
import com.example.authservice.dto.ResponseDTO;
import com.example.authservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-profile-service", fallback = UserProfileClientImpl.class)
public interface IUserProfileClient {
    @GetMapping("/user/{username}")
    ResponseEntity<ResponseDTO<UserDTO>> getByUsername(@PathVariable("username") String username);
}
