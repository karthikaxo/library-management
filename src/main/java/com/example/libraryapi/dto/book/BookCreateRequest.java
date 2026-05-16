package com.example.libraryapi.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getters, setters, toString, equals, and hashCode methods
@AllArgsConstructor
@NoArgsConstructor
public class BookCreateRequest {
    // no id

    @NotBlank
    private String isbn;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String category;

    @Positive
    private int yearPublished;
}
