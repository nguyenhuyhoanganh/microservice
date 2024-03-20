package com.example.authservice.security;

import com.example.authservice.client.IUserProfileClient;
import com.example.authservice.dto.UserDTO;
import com.example.authservice.entity.User;
import com.example.authservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserProfileClient userProfileClient;

    public CustomUserDetailsService(IUserProfileClient userProfileClient) {
        this.userProfileClient = userProfileClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = userProfileClient.getByUsername(username).getBody().getData();
        return new CustomUserDetails(user);
    }

}

