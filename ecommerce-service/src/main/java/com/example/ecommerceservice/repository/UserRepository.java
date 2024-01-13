package com.example.ecommerceservice.repository;

import com.example.ecommerceservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
