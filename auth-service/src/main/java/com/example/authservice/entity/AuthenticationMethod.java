package com.example.authservice.entity;

import com.example.authservice.enumeration.AuthenticationMethodEnum;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authentication_methods")
public class AuthenticationMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AuthenticationMethodEnum authenticationMethod;
}
