package com.example.ecommerceservice.service.impl;

import com.example.ecommerceservice.dto.RoleDTO;
import com.example.ecommerceservice.dto.UserInfoDTO;
import com.example.ecommerceservice.service.IUserInfoService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserInfoServiceImpl implements IUserInfoService {
    @Value("${security.oauth2.userinfo.endpoint}")
    String userInfoUrl;
    @Autowired
    private RestTemplate restTemplate;

    public static UserInfoDTO convertToUserInfo(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> jsonObject = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
            UserInfoDTO userInfoDTO = new UserInfoDTO();

            // user_info
            Map<String, Object> userInfoJson = (Map<String, Object>) jsonObject.get("user_info");
            userInfoDTO.setId((Integer) userInfoJson.get("id"));
            userInfoDTO.setUsername((String) jsonObject.get("sub"));
            userInfoDTO.setLastName((String) userInfoJson.get("lastName"));
            userInfoDTO.setFirstName((String) userInfoJson.get("firstName"));
            Date createdAt  = Date.from(Instant.ofEpochMilli((Long) userInfoJson.get("createdAt")));
            Date modifiedAt  = Date.from(Instant.ofEpochMilli((Long) userInfoJson.get("modifiedAt")));
            userInfoDTO.setCreatedAt(createdAt);
            userInfoDTO.setModifiedAt(modifiedAt);

            // roles
            List<String> listRoleJson = (List<String>) jsonObject.get("roles");
            List<RoleDTO> roles = listRoleJson.stream()
                    .map(roleJson -> {
                        return RoleDTO.builder().name(roleJson).build();
                    })
                    .collect(Collectors.toList());
            userInfoDTO.setRoles(roles);

            return userInfoDTO;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UserInfoDTO getUserInfoFromAuthServer(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<?> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                userInfoUrl,
                HttpMethod.GET,
                request,
                String.class
        );
        if (response.getStatusCode().is2xxSuccessful()) {
            UserInfoDTO userInfo = convertToUserInfo(response.getBody());
            return userInfo;
        } else {
            throw new RuntimeException("Failed to get user info from auth server");
        }
    }
}
