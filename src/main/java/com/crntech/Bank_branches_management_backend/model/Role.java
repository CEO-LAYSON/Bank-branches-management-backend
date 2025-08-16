package com.crntech.Bank_branches_management_backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String name;

    private String description;
}
