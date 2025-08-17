package com.crntech.Bank_branches_management_backend.mapper;

import com.crntech.Bank_branches_management_backend.dto.UserDto;
import com.crntech.Bank_branches_management_backend.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "roles", expression = "java(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet()))")
    @Mapping(target = "branchId", source = "branch.id")
    @Mapping(target = "branchName", source = "branch.name")
    UserDto toDto(User user);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "branch", ignore = true)
    User toEntity(UserDto userDto);
}