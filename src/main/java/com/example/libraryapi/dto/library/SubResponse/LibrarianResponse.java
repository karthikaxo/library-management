package com.example.libraryapi.dto.library.SubResponse;

import com.example.libraryapi.entity.Role;
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
public class LibrarianResponse {
    @Positive
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String email;

    @NotNull
    private Role role;
}
