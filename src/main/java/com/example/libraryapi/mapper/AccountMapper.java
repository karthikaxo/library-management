package com.example.libraryapi.mapper;

import com.example.libraryapi.dto.account.SearchResponse.LibrarianSearchResponse;
import com.example.libraryapi.dto.account.SearchResponse.MemberSearchResponse;
import com.example.libraryapi.dto.account.LibrarianCreate.LibrarianCreateResponse;
import com.example.libraryapi.dto.account.MemberCreate.MemberCreateResponse;
import com.example.libraryapi.dto.account.Update.LibrarianUpdate;
import com.example.libraryapi.dto.account.Update.MemberUpdate;
import com.example.libraryapi.dto.bookItem.SearchResponseExtra.LendingResponse;
import com.example.libraryapi.dto.bookItem.SearchResponseExtra.LibraryResponse;
import com.example.libraryapi.entity.Account;
import com.example.libraryapi.entity.Lending;
import com.example.libraryapi.entity.LendingStatus;

import java.util.Comparator;
import java.util.List;

public class AccountMapper {

    public static LibrarianCreateResponse LibCreateToResponse(Account account) {
        return LibrarianCreateResponse.builder()
                .username(account.getUsername())
                .email(account.getEmail())
                .role(account.getRole())
                .libraryAddress(account.getLibrary().getAddress())
                .createDate(account.getCreateDate())
                .build();
    }

    public static MemberCreateResponse MemCreateToResponse(Account account) {
        return MemberCreateResponse.builder()
                .username(account.getUsername())
                .email(account.getEmail())
                .role(account.getRole())
                .createDate(account.getCreateDate())
                .build();
    }

    public static MemberSearchResponse searchMemToResponse(Account account) {

        // most recent lending
        Lending lending = account.getLendings().stream()
                .filter(lending1 -> lending1.getStatus() == LendingStatus.ACTIVE ||
                        lending1.getStatus() == LendingStatus.LOST)
                .max(Comparator.comparing(Lending::getBorrowDate))
                .orElse(null);

        LendingResponse lendingResponse = null;
        if (lending != null) {
            lendingResponse = LendingResponse.builder()
                    .id(lending.getId())
                    .borrowDate(lending.getBorrowDate())
                    .dueDate(lending.getDueDate())
                    .status(lending.getStatus())
                    .build();
        }

        return MemberSearchResponse.builder()
                .id(account.getId())
                .username(account.getUsername())
                .email(account.getEmail())
                .createDate(account.getCreateDate())
                .lastLogin(account.getLastLogin())
                .lending(lendingResponse) // nullable
                .build();
    }

    public static List<MemberSearchResponse> searchMemToResponseList(List<Account> accounts) {
        return accounts.stream().map(AccountMapper::searchMemToResponse).toList();
    }

    public static LibrarianSearchResponse searchLibToResponse(Account account) {

        // assigned library
        LibraryResponse libraryResponse = LibraryResponse.builder()
                .name(account.getLibrary().getName())
                .address(account.getLibrary().getAddress())
                .build();

        return LibrarianSearchResponse.builder()
                .id(account.getId())
                .username(account.getUsername())
                .email(account.getEmail())
                .createDate(account.getCreateDate())
                .lastLogin(account.getLastLogin())
                .library(libraryResponse)
                .build();
    }

    public static List<LibrarianSearchResponse> searchLibToResponseList(List<Account> accounts) {
        return accounts.stream().map(AccountMapper::searchLibToResponse).toList();
    }

    public static MemberUpdate updateMemToResponse(Account account) {
        return MemberUpdate.builder()
                .username(account.getUsername())
                .email(account.getEmail())
                .build();
    }

    public static LibrarianUpdate updateLibToResponse(Account account) {
        return LibrarianUpdate.builder()
                .libraryID(account.getLibrary().getId())
                .username(account.getUsername())
                .email(account.getEmail())
                .build();
    }




}
