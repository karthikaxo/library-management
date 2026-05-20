package com.example.libraryapi.dto.library;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getters, setters, toString, equals, and hashCode methods
@AllArgsConstructor
@NoArgsConstructor
public class LibraryCreateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String address;

}
