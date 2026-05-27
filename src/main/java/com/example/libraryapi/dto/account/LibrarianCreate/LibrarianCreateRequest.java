package com.example.libraryapi.dto.account.LibrarianCreate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getters, setters, toString, equals, and hashCode methods
@AllArgsConstructor
@NoArgsConstructor
public class LibrarianCreateRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotNull
    @Positive
    private Long libraryID; // assigned library
}
