package com.crntech.Bank_branches_management_backend.mapper;

import com.crntech.Bank_branches_management_backend.dto.BranchDto;
import com.crntech.Bank_branches_management_backend.model.Branch;
import org.mapstruct.factory.Mappers;

public interface BranchMapper {

    BranchMapper INSTANCE = Mappers.getMapper(BranchMapper.class);

    BranchDto toDto(Branch branch);
    Branch toEntity(BranchDto branchDto);
}
