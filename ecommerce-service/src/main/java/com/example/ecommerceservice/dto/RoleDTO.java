package com.example.ecommerceservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
}
