package com.example.userprofileservice.service.impl;

import com.example.userprofileservice.dto.UserDTO;
import com.example.userprofileservice.entity.User;
import com.example.userprofileservice.repository.UserRepository;
import com.example.userprofileservice.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository repository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public UserDTO getUserByUserName(String username) {
        User user = repository.findByUsername(username).orElseThrow(() -> new RuntimeException());
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getUsers(Pageable pageable) {
        return null;
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        return null;
    }
}
