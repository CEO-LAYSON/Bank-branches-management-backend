package com.crntech.Bank_branches_management_backend.mapper;

import com.crntech.Bank_branches_management_backend.dto.BranchDto;
import com.crntech.Bank_branches_management_backend.model.Branch;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BranchMapper {
    BranchDto toDto(Branch branch);
    Branch toEntity(BranchDto branchDto);
}
