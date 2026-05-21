package com.example.libraryapi.repository;

import com.example.libraryapi.entity.BookItem;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookItemRepository extends JpaRepository<BookItem, Long>,
        JpaSpecificationExecutor<BookItem> {
    boolean existsByBarcode(@NotBlank String barcode);

    BookItem findByBarcode(String barcode);
}
