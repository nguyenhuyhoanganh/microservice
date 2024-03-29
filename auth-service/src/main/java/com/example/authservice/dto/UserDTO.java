package com.example.authservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @NotBlank
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firstName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;

    @NotBlank
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String username;

    @NotBlank
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String credential; // password

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String phoneNumber;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean isActive;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<RoleDTO> roles;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date modifiedAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date createdAt;
}
