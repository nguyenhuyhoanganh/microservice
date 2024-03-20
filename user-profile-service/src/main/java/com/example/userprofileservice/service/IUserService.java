package com.example.userprofileservice.service;

import com.example.userprofileservice.dto.UserDTO;

import java.awt.print.Pageable;
import java.util.List;

public interface IUserService {
    UserDTO getUserByUserName(String username);

    List<UserDTO> getUsers(Pageable pageable);

    UserDTO saveUser(UserDTO userDTO);
}
