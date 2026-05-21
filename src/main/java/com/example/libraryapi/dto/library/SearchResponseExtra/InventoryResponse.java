package com.example.libraryapi.dto.library.SearchResponseExtra;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponse {
    @Positive
    private Long id;

    @NotBlank
    private String title;

    @PositiveOrZero
    private int copies;

}
