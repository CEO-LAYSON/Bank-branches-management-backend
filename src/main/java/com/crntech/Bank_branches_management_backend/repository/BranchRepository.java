package com.crntech.Bank_branches_management_backend.repository;

import com.crntech.Bank_branches_management_backend.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    Optional<Branch> findByName(String name);
    Optional<Branch> findByCode(String code);
    boolean existsByName(String name);
    boolean existsByCode(String code);
}
