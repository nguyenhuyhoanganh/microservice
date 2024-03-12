package com.example.ecommerceservice.controller;

import com.example.ecommerceservice.client.INotificationServiceClient;
import com.example.ecommerceservice.dto.ProductDTO;
import com.example.ecommerceservice.dto.ResponseDTO;
import com.example.ecommerceservice.dto.UserInfoDTO;
import com.example.ecommerceservice.service.IProductService;
import com.example.ecommerceservice.service.IUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService service;
    @Autowired
    private IUserInfoService userInfoService;
//    @Autowired
//    private INotificationServiceClient notificationClient;

    @GetMapping("")
    public ResponseEntity<ResponseDTO<List<ProductDTO>>> getAll(@RequestHeader("Authorization") String authorizationHeader){

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring(7);
            UserInfoDTO userInfo = userInfoService.getUserInfoFromAuthServer(accessToken);
            log.info(userInfo.toString());
        }

//        notificationClient.inform(authorizationHeader);
        ResponseDTO<List<ProductDTO>> responseBody = ResponseDTO.<List<ProductDTO>>builder().data(service.getAll()).build();
        return new ResponseEntity<ResponseDTO<List<ProductDTO>>>(responseBody, HttpStatus.OK);
    }
}

