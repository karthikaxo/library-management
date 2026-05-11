package com.example.libraryapi.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Library {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String address;

    @OneToMany(mappedBy = "library")
    private List<BookItem> inventory; // book items in a library

    @OneToMany(mappedBy = "library")
    private List<Account> accounts; // 'LIBRARIAN'(s) in a specific library
}
