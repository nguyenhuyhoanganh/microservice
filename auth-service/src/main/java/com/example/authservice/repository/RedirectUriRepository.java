package com.example.authservice.repository;

import com.example.authservice.entity.RedirectUri;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RedirectUriRepository extends JpaRepository<RedirectUri, Long> {
    Optional<RedirectUri> findByUri(String uri);
}
