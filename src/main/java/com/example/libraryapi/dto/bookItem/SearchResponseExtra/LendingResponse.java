package com.example.libraryapi.dto.bookItem.SearchResponseExtra;

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
public class LendingResponse {

    @Positive
    private Long id;

    @NotNull
    private LocalDate borrowDate;

    @NotNull
    private LocalDate dueDate;

    @NotNull
    private LendingStatus status; // LOST or ACTIVE
}
