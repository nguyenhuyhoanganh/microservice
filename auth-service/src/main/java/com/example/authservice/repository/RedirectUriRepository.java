package com.example.authservice.repository;

import com.example.authservice.entity.Client;
import com.example.authservice.entity.RedirectUri;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface RedirectUriRepository extends JpaRepository<RedirectUri, Long> {

    Optional<RedirectUri> findByUri(String uri);

    Optional<Set<RedirectUri>> findByClientId(Long clientId);

    Set<RedirectUri> findByClient(Client client);
}
