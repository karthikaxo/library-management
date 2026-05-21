package com.example.libraryapi.dto.bookItem.SearchResponseExtra;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LibraryResponse {

    @NotBlank
    private String name;

    @NotBlank
    private String address;

}
