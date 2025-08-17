package com.crntech.Bank_branches_management_backend.dto;

import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Set<String> roles;
    private Long branchId;
    private String branchName;
    private Boolean active;
}
