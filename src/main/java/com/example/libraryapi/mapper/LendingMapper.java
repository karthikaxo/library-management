package com.example.libraryapi.mapper;


import com.example.libraryapi.dto.lending.LendingResponse;
import com.example.libraryapi.dto.lending.LendingSearchResponse;
import com.example.libraryapi.dto.lending.SearchResponseExtra.AccountResponse;
import com.example.libraryapi.dto.lending.SearchResponseExtra.BookItemResponse;
import com.example.libraryapi.dto.lending.SearchResponseExtra.FineResponse;
import com.example.libraryapi.entity.Lending;

import java.util.List;

public class LendingMapper {

    public static LendingResponse createToResponse(Lending lending) {
        return LendingResponse.builder()
                .id(lending.getId())
                .borrowedBy(lending.getAccount().getUsername())
                .barcode(lending.getBookItem().getBarcode())
                .borrowDate(lending.getBorrowDate())
                .dueDate(lending.getDueDate())
                .status(lending.getStatus())
                .build();
    }

    public static LendingSearchResponse searchToResponse(Lending lending) {

        AccountResponse account = AccountResponse.builder()
                .id(lending.getAccount().getId())
                .username(lending.getAccount().getUsername())
                .build();

        BookItemResponse bookItem = BookItemResponse.builder()
                .id(lending.getBookItem().getId())
                .title(lending.getBookItem().getBook().getTitle())
                .library(lending.getBookItem().getLibrary().getAddress())
                .barcode(lending.getBookItem().getBarcode())
                .build();

        FineResponse fine = null;
        if (lending.getFine() != null) {
            fine = FineResponse.builder()
                    .id(lending.getFine().getId())
                    .status(lending.getFine().getStatus())
                    .build();
        }

        return LendingSearchResponse.builder()
                .id(lending.getId())
                .account(account)
                .bookItem(bookItem)
                .borrowDate(lending.getBorrowDate())
                .dueDate(lending.getDueDate())
                .returnDate(lending.getReturnDate())
                .status(lending.getStatus())
                .fine(fine)
                .build();
    }

    public static List<LendingSearchResponse> searchToResponseList(List<Lending> lendings) {
        return (lendings
                .stream().map(LendingMapper::searchToResponse)
                .toList());
    }
}
