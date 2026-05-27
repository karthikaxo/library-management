package com.example.libraryapi.dto.bookItem;

import com.example.libraryapi.entity.BookItemStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookItemCreateResponse {

    @NotNull
    @Positive
    private Long id;

    @NotBlank
    private String isbn; // ISBN of Book

    @NotNull
    private LocalDate dateAcquired;

    @NotBlank
    private String address; // address of Library

    @NotBlank
    private String barcode;

}
