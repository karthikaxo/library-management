package com.example.libraryapi.repository;

import com.example.libraryapi.entity.Account;
import com.example.libraryapi.entity.Role;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;



public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {
    Account findByUsername(String username);

    boolean existsByUsername(@NotBlank String username);

    boolean existsByEmail(@NotBlank String email);

    Account findByIdAndRole(Long id, Role role);

    Account findByUsernameAndRole(String username, Role role);

    Account findByEmailAndRole(String email, Role role);

    Account findByEmail(String email);
}
