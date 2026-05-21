package com.example.libraryapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table
@Data // getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private int yearPublished;

    // cascade: propagate changes from Book to BookItem
    // orphan removal: removal of BookItem from list deletes it from db
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookItem> copies; // copies of the same book

    // helper methods

    // used in BookItemServiceImpl
    public void addBookItem(BookItem bookItem) {
        copies.add(bookItem);
        bookItem.setBook(this);
    }
}
