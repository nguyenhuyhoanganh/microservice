package com.example.authservice.repository;

import com.example.authservice.entity.GrantType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GrantTypeRepository extends JpaRepository<GrantType, Long> {
    @Query("SELECT gt FROM GrantType gt WHERE gt.grantType = :grantType")
    Optional<GrantType> findByGrantType(String grantType);
}
