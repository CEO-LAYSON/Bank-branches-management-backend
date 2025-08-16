package com.crntech.Bank_branches_management_backend.dto;

import lombok.*;

import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String username;
    private String email;
    private Set<String> roles;
    private Long branchId;
    private String branchName;
}