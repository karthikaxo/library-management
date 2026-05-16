package com.example.libraryapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Data // getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor
@AllArgsConstructor
public class BookItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false)
    private LocalDate dateAcquired;

    @ManyToOne
    @JoinColumn(name = "library_id", nullable = false)
    private Library library; // location of book

    @Column(unique = true, nullable = false)
    private String barcode; // ISBN + copy no.

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookItemStatus status;

    @OneToMany(mappedBy = "bookItem")
    private List<Lending> lendings;
}
