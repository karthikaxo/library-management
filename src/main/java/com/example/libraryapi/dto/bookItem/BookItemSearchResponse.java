package com.example.libraryapi.dto.bookItem;

import com.example.libraryapi.dto.bookItem.SearchResponseExtra.BookResponse;
import com.example.libraryapi.dto.bookItem.SearchResponseExtra.LendingResponse;
import com.example.libraryapi.dto.bookItem.SearchResponseExtra.LibraryResponse;
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
public class BookItemSearchResponse {

    @NotNull
    @Positive
    private Long id;

    @NotNull
    private BookResponse book;

    @NotNull
    private LocalDate dateAcquired;

    @NotNull
    private LibraryResponse library;

    @NotBlank
    private String barcode;

    @NotNull
    private BookItemStatus status;

    private LendingResponse lending; // only if status == UNAVAILABLE, most recent lending
}
