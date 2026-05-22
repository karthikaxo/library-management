package com.example.libraryapi.mapper;

import com.example.libraryapi.dto.account.LibrarianCreate.LibrarianCreateResponse;
import com.example.libraryapi.dto.account.MemberCreate.MemberCreateResponse;
import com.example.libraryapi.entity.Account;

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


}
