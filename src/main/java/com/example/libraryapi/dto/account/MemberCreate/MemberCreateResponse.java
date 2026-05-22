package com.example.libraryapi.dto.account.MemberCreate;

import com.example.libraryapi.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberCreateResponse {

    @NotBlank
    private String username;

    @NotBlank
    private String email;

    @NotNull
    private Role role;

    @NotNull
    private LocalDate createDate;
}
