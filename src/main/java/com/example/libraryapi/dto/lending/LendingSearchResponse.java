package com.example.libraryapi.dto.lending;

import com.example.libraryapi.dto.lending.SearchResponseExtra.AccountResponse;
import com.example.libraryapi.dto.lending.SearchResponseExtra.BookItemResponse;
import com.example.libraryapi.dto.lending.SearchResponseExtra.FineResponse;
import com.example.libraryapi.entity.LendingStatus;
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
public class LendingSearchResponse {

    @NotNull
    @Positive
    private Long id;

    @NotNull
    private AccountResponse account;

    @NotNull
    private BookItemResponse bookItem;

    @NotNull
    private LocalDate borrowDate;

    @NotNull
    private LocalDate dueDate;

    // nullable
    private LocalDate returnDate;

    @NotNull
    private LendingStatus status;

    // nullable
    private FineResponse fine;

}
