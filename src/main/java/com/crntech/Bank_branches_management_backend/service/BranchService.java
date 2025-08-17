package com.crntech.Bank_branches_management_backend.service;

import com.crntech.Bank_branches_management_backend.mapper.BranchMapper;
import com.crntech.Bank_branches_management_backend.dto.BranchDto;
import com.crntech.Bank_branches_management_backend.exception.ResourceNotFoundException;
import com.crntech.Bank_branches_management_backend.model.Branch;
import com.crntech.Bank_branches_management_backend.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;

    public BranchDto createBranch(BranchDto branchDto) {
        // Check if branch name or code already exists
        if (branchRepository.existsByName(branchDto.getName())) {
            throw new RuntimeException("Branch name already exists");
        }

        if (branchRepository.existsByCode(branchDto.getCode())) {
            throw new RuntimeException("Branch code already exists");
        }

        Branch branch = branchMapper.toEntity(branchDto);
        Branch savedBranch = branchRepository.save(branch);
        return branchMapper.toDto(savedBranch);
    }

    public List<BranchDto> getAllBranches() {
        return branchRepository.findAll().stream()
                .map(branchMapper::toDto)
                .collect(Collectors.toList());
    }

    public BranchDto getBranchById(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", "id", id));
        return branchMapper.toDto(branch);
    }

    public Branch getBranchEntityById(Long id) {
        return branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", "id", id));
    }

    public BranchDto updateBranch(Long id, BranchDto branchDto) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", "id", id));

        if (branchDto.getName() != null && !branchDto.getName().equals(branch.getName())) {
            if (branchRepository.existsByName(branchDto.getName())) {
                throw new RuntimeException("Branch name already exists");
            }
            branch.setName(branchDto.getName());
        }

        if (branchDto.getCode() != null && !branchDto.getCode().equals(branch.getCode())) {
            if (branchRepository.existsByCode(branchDto.getCode())) {
                throw new RuntimeException("Branch code already exists");
            }
            branch.setCode(branchDto.getCode());
        }

        if (branchDto.getAddress() != null) {
            branch.setAddress(branchDto.getAddress());
        }

        if (branchDto.getCity() != null) {
            branch.setCity(branchDto.getCity());
        }

        if (branchDto.getState() != null) {
            branch.setState(branchDto.getState());
        }

        if (branchDto.getCountry() != null) {
            branch.setCountry(branchDto.getCountry());
        }

        if (branchDto.getPostalCode() != null) {
            branch.setPostalCode(branchDto.getPostalCode());
        }

        if (branchDto.getPhone() != null) {
            branch.setPhone(branchDto.getPhone());
        }

        if (branchDto.getEmail() != null) {
            branch.setEmail(branchDto.getEmail());
        }

        if (branchDto.getActive() != null) {
            branch.setActive(branchDto.getActive());
        }

        Branch updatedBranch = branchRepository.save(branch);
        return branchMapper.toDto(updatedBranch);
    }

    public void deleteBranch(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", "id", id));
        branchRepository.delete(branch);
    }
}
