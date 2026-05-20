package com.example.libraryapi.repository;

import com.example.libraryapi.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LibraryRepository extends JpaRepository<Library, Long>,
        JpaSpecificationExecutor<Library> {

    boolean existsByAddress(String address);

    Library findByAddress(String address);
}
