package com.example.libraryapi.dto.bookItem;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookItemCreateRequest {

    @NotBlank
    private String isbn; // ISBN of Book

    @NotBlank
    private String address; // address of Library

    @NotBlank
    private String barcode;
}
