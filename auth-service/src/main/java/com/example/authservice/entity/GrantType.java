package com.example.authservice.entity;

import com.example.authservice.enumeration.GrantTypeEnum;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "grant_types")
public class GrantType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private GrantTypeEnum grantType;

}
