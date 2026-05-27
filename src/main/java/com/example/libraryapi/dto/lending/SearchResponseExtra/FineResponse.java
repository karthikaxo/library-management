package com.example.libraryapi.dto.lending.SearchResponseExtra;

import com.example.libraryapi.entity.FineStatus;
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
public class FineResponse {

    @NotNull
    @Positive
    private Long id;

    @NotNull
    private FineStatus status;
}
