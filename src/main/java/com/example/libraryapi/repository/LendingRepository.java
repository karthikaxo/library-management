package com.example.libraryapi.repository;

import com.example.libraryapi.entity.Account;
import com.example.libraryapi.entity.Lending;
import com.example.libraryapi.entity.LendingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LendingRepository extends JpaRepository<Lending, Long> {
    List<Lending> findByAccount(Account account);

    List<Lending> findByAccountAndStatus(Account account, LendingStatus lendingStatus);

    List<Lending> findByDueDateBeforeAndStatus(LocalDate now, LendingStatus lendingStatus);
}
