package com.example.authservice.repository;

import com.example.authservice.entity.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ScopeRepository extends JpaRepository<Scope, Long> {
    @Query("SELECT s FROM Scope s WHERE s.scopeName = :scopeName")
    Optional<Scope> findByScopeName(String scopeName);
}
