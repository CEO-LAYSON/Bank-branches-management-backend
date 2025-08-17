package com.crntech.Bank_branches_management_backend.mapper;

import com.crntech.Bank_branches_management_backend.dto.RoleDto;
import com.crntech.Bank_branches_management_backend.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDto toDto(Role role);
    Role toEntity(RoleDto roleDto);
}