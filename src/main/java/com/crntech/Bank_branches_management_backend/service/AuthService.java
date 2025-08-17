package com.crntech.Bank_branches_management_backend.service;

import com.crntech.Bank_branches_management_backend.dto.AuthRequest;
import com.crntech.Bank_branches_management_backend.dto.AuthResponse;
import com.crntech.Bank_branches_management_backend.dto.UserDto;
import com.crntech.Bank_branches_management_backend.exception.BlogAPIException;
import com.crntech.Bank_branches_management_backend.mapper.UserMapper;
import com.crntech.Bank_branches_management_backend.model.User;
import com.crntech.Bank_branches_management_backend.repository.UserRepository;
import com.crntech.Bank_branches_management_backend.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;
    private final UserMapper userMapper;
    
    public AuthResponse login(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(),
                authRequest.getPassword()
            )
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String token = tokenProvider.generateToken(authentication);
        
        User user = userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new BlogAPIException(HttpStatus.BAD_REQUEST, "User not found"));
        
        Set<String> roles = user.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toSet());
        
        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(roles)
                .branchId(user.getBranch() != null ? user.getBranch().getId() : null)
                .branchName(user.getBranch() != null ? user.getBranch().getName() : null)
                .build();
    }
    
    public UserDto getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userMapper.toDto(userRepository.findById(user.getId()).orElseThrow());
    }
}

