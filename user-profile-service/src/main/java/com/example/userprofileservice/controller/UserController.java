package com.example.userprofileservice.controller;


import com.example.userprofileservice.dto.ResponseDTO;
import com.example.userprofileservice.dto.UserDTO;
import com.example.userprofileservice.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService service;


    @GetMapping("/{username}")
    public ResponseEntity<ResponseDTO<UserDTO>> getByUsername(@PathVariable("username") String username){

        ResponseDTO<UserDTO> responseBody = ResponseDTO.<UserDTO>builder().data(service.getUserByUserName(username)).build();

        return new ResponseEntity<ResponseDTO<UserDTO>>(responseBody, HttpStatus.OK);
    }
}
