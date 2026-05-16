package com.example.libraryapi.repository;

import com.example.libraryapi.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface BookRepository extends JpaRepository<Book, Long>,
        JpaSpecificationExecutor<Book> {
    // specifications for dynamic queries
}
