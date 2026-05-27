package com.example.libraryapi.dto.library;

import jakarta.validation.constraints.NotBlank;
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
public class LibraryCreateResponse {

    @NotNull
    @Positive
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String address;
}
