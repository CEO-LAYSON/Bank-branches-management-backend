package com.crntech.Bank_branches_management_backend.mapper;

import com.crntech.Bank_branches_management_backend.dto.UserDto;
import com.crntech.Bank_branches_management_backend.model.Role;
import com.crntech.Bank_branches_management_backend.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoles")
    @Mapping(target = "branchId", source = "branch.id")
    @Mapping(target = "branchName", source = "branch.name")
    UserDto toDto(User user);

    @Mapping(target = "roles", ignore = true)   // handled separately
    @Mapping(target = "branch", ignore = true) // handled separately
    User toEntity(UserDto userDto);

    // 🔹 Helper method to map Role entities -> role names
    @Named("mapRoles")
    default Set<String> mapRoles(Set<Role> roles) {
        return roles == null ? null :
                roles.stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet());
    }
}
