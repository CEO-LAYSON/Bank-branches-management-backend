package com.crntech.Bank_branches_management_backend.service;

import com.crntech.Bank_branches_management_backend.dto.RoleDto;
import com.crntech.Bank_branches_management_backend.exception.ResourceNotFoundException;
import com.crntech.Bank_branches_management_backend.mapper.RoleMapper;
import com.crntech.Bank_branches_management_backend.model.Role;
import com.crntech.Bank_branches_management_backend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleDto createRole(RoleDto roleDto) {
        if (roleRepository.existsByName(roleDto.getName())) {
            throw new RuntimeException("Role name already exists");
        }

        Role role = roleMapper.toEntity(roleDto);
        Role savedRole = roleRepository.save(role);
        return roleMapper.toDto(savedRole);
    }

    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
    }

    public RoleDto getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
        return roleMapper.toDto(role);
    }

    public RoleDto updateRole(Long id, RoleDto roleDto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));

        if (roleDto.getName() != null && !roleDto.getName().equals(role.getName())) {
            if (roleRepository.existsByName(roleDto.getName())) {
                throw new RuntimeException("Role name already exists");
            }
            role.setName(roleDto.getName());
        }

        if (roleDto.getDescription() != null) {
            role.setDescription(roleDto.getDescription());
        }

        role.setActive(roleDto.isActive()); // use boolean getter

        Role updatedRole = roleRepository.save(role);
        return roleMapper.toDto(updatedRole);
    }

    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
        roleRepository.delete(role);
    }
}
