package com.example.libraryapi.mapper;

import com.example.libraryapi.dto.bookItem.SearchResponseExtra.LendingResponse;
import com.example.libraryapi.dto.fine.FineResponse;
import com.example.libraryapi.entity.Fine;

public class FineMapper {

    public static FineResponse toResponse(Fine fine) {

        LendingResponse lending = LendingResponse.builder()
                .id(fine.getLending().getId())
                .borrowDate(fine.getLending().getBorrowDate())
                .dueDate(fine.getLending().getDueDate())
                .status(fine.getLending().getStatus())
                .build();

        return FineResponse.builder()
                .id(fine.getId())
                .amount(fine.getAmount())
                .lending(lending)
                .createdAt(fine.getCreatedAt())
                .status(fine.getStatus())
                .build();
    }
}
