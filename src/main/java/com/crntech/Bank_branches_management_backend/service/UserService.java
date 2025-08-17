package com.crntech.Bank_branches_management_backend.service;

import java.util.Set;
import org.springframework.security.core.context.SecurityContextHolder;
import com.crntech.Bank_branches_management_backend.dto.UserDto;
import com.crntech.Bank_branches_management_backend.exception.ResourceNotFoundException;
import com.crntech.Bank_branches_management_backend.mapper.UserMapper;
import com.crntech.Bank_branches_management_backend.model.Role;
import com.crntech.Bank_branches_management_backend.model.User;
import com.crntech.Bank_branches_management_backend.repository.RoleRepository;
import com.crntech.Bank_branches_management_backend.repository.UserRepository;
import com.crntech.Bank_branches_management_backend.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BranchService branchService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final CustomUserDetailsService userDetailsService;

    public UserDto createUser(UserDto userDto) {
        // Check if username or email already exists
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }

        // Create new user
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Set roles
        if (userDto.getRoles() != null && !userDto.getRoles().isEmpty()) {
            Set<Role> roles = userDto.getRoles().stream()
                    .map(roleName -> roleRepository.findByName(roleName)
                            .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        } else {
            // Default role if none specified
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Default role not found"));
            user.setRoles(Collections.singleton(userRole));
        }

        // Set branch if specified
        if (userDto.getBranchId() != null) {
            user.setBranch(branchService.getBranchEntityById(userDto.getBranchId()));
        }

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return userMapper.toDto(user);
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        if (userDto.getFirstName() != null) {
            user.setFirstName(userDto.getFirstName());
        }

        if (userDto.getLastName() != null) {
            user.setLastName(userDto.getLastName());
        }

        if (userDto.getEmail() != null && !userDto.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(userDto.getEmail())) {
                throw new RuntimeException("Email is already in use");
            }
            user.setEmail(userDto.getEmail());
        }

        if (userDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        if (userDto.getRoles() != null && !userDto.getRoles().isEmpty()) {
            Set<Role> roles = userDto.getRoles().stream()
                    .map(roleName -> roleRepository.findByName(roleName)
                            .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }

        if (userDto.getBranchId() != null) {
            user.setBranch(branchService.getBranchEntityById(userDto.getBranchId()));
        }

        if (userDto.getActive() != null) {
            user.setActive(userDto.getActive());
        }

        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(user);
    }

    public UserDto getCurrentUserDetails() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userMapper.toDto(userRepository.findById(user.getId()).orElseThrow());
    }

    public List<UserDto> getUsersByBranch(Long branchId) {
        return userRepository.findByBranchId(branchId).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
}
