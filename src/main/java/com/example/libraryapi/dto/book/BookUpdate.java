package com.example.libraryapi.dto.book;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getters, setters, toString, equals, and hashCode methods
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookUpdate {
    // can be null
    private String isbn;
    private String title;
    private String author;
    private String category;
    @Min(1000)
    @Max(2100)
    private Integer yearPublished;
}
