package com.example.libraryapi.dto.lending;

import com.example.libraryapi.entity.LendingStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// used in both CREATE and UPDATE
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LendingResponse {

    @NotNull
    @Positive
    private Long id;

    @NotBlank
    private String borrowedBy; // account username

    @NotBlank
    private String barcode; // book item

    @NotNull
    private LocalDate borrowDate;

    @NotNull
    private LocalDate dueDate;

    @NotNull
    private LendingStatus status;

}
