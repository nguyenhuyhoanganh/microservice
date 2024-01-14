package com.example.authservice.repository;

import com.example.authservice.entity.AuthenticationMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthenticationMethodRepository extends JpaRepository<AuthenticationMethod, Long> {
    @Query("SELECT am FROM AuthenticationMethod am WHERE am.authenticationMethod = :authenticationMethod")
    Optional<AuthenticationMethod> findByAuthenticationMethod(String authenticationMethod);
}
