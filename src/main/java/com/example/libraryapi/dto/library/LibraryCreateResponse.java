package com.example.libraryapi.dto.library;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LibraryCreateResponse {
    @PositiveOrZero
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String address;
}
