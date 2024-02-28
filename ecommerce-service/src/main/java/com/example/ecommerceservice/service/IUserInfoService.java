package com.example.ecommerceservice.service;

import com.example.ecommerceservice.dto.UserInfoDTO;

public interface IUserInfoService {
    UserInfoDTO getUserInfoFromAuthServer(String accessToken);
}


