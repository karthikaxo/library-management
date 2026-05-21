package com.example.libraryapi.dto.library;

import com.example.libraryapi.dto.library.SearchResponseExtra.InventoryResponse;
import com.example.libraryapi.dto.library.SearchResponseExtra.LibrarianResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LibrarySearchResponse {
    @PositiveOrZero
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotNull
    private List<InventoryResponse> inventory;

    @NotNull
    private List<LibrarianResponse> accounts;
}
