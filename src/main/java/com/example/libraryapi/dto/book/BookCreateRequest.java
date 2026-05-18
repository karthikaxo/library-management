package com.example.libraryapi.dto.book;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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

    @Min(1000)
    @Max(2026)
    private int yearPublished;
}
