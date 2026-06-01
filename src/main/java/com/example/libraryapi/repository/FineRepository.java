package com.example.libraryapi.repository;

import com.example.libraryapi.entity.Fine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FineRepository extends JpaRepository<Fine, Long> {
    boolean existsByLendingId(Long id);
}
