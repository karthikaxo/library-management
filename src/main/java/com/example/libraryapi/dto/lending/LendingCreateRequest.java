package com.example.libraryapi.dto.lending;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LendingCreateRequest {

    @NotNull
    @Positive
    private Long accountID; // account

    @NotBlank
    private String barcode; // book item

    @NotNull
    private LocalDate borrowDate;

    @NotNull
    private LocalDate dueDate;

    // status in-built

}
