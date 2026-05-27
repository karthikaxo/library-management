package com.example.libraryapi.dto.lending.SearchResponseExtra;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookItemResponse {

    @NotNull
    @Positive
    private Long id;

    @NotBlank
    private String title; // Book title

    @NotBlank
    private String library; // Library address

    @NotBlank
    private String barcode;
}
