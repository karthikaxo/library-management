package com.example.libraryapi.dto.fine;

import com.example.libraryapi.dto.bookItem.SearchResponseExtra.LendingResponse;
import com.example.libraryapi.entity.FineStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FineResponse {

    @NotNull
    @Positive
    private Long id;

    @NotNull
    @PositiveOrZero
    private int amount;

    @NotNull
    private LendingResponse lending;

    @NotNull
    private LocalDate createdAt;

    @NotNull
    private FineStatus status;
}
