package com.example.libraryapi.dto.book;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookSearchResponse {

    @PositiveOrZero
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String category;

    @Min(1000)
    @Max(2026)
    private int yearPublished;

    // info to be returned when searched, not included in Book entity

    // defaults to false
    private boolean available;

    @NotNull // allows empty list
    private List<String> locations; // available locations
}
