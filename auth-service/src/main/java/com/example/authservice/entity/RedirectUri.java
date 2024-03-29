package com.example.authservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "redirect_uris")
public class RedirectUri {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uri;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "is_logout_uri")
    private boolean isLogoutUri;
}
