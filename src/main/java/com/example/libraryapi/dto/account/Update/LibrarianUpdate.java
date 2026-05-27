package com.example.libraryapi.dto.account.Update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LibrarianUpdate {

    private Long libraryID;

    private String username;

    private String email;
}
