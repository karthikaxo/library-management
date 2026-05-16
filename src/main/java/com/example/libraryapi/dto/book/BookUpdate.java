package com.example.libraryapi.dto.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data // getters, setters, toString, equals, and hashCode methods
@AllArgsConstructor
@Builder
public class BookUpdate {
    // can be null
    private String isbn;
    private String title;
    private String author;
    private String category;
    private int yearPublished;
}
