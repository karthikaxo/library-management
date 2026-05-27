package com.example.libraryapi.dto.account.SearchResponse;

import com.example.libraryapi.dto.bookItem.SearchResponseExtra.LendingResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberSearchResponse {

    @Positive
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String email;

    @NotNull
    private LocalDate createDate;

    @NotNull
    private LocalDate lastLogin;

    private LendingResponse lending; // most recent lending
}
