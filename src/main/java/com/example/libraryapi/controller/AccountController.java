package com.example.libraryapi.controller;

import com.example.libraryapi.dto.account.LibrarianCreate.LibrarianCreateRequest;
import com.example.libraryapi.dto.account.LibrarianCreate.LibrarianCreateResponse;
import com.example.libraryapi.dto.account.Login.LoginRequest;
import com.example.libraryapi.dto.account.Login.LoginResponse;
import com.example.libraryapi.dto.account.MemberCreate.MemberCreateRequest;
import com.example.libraryapi.dto.account.MemberCreate.MemberCreateResponse;
import com.example.libraryapi.service.AccountServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;


    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/create-lib")
    public LibrarianCreateResponse saveLibrarianAccount(@Valid @RequestBody LibrarianCreateRequest createRequest) {
        return accountService.saveLibrarianAccount(createRequest);
    }

    @PostMapping("/create-mem")
    public MemberCreateResponse saveMemberAccount(@Valid @RequestBody MemberCreateRequest createRequest) {
        return accountService.saveMemberAccount(createRequest);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return accountService.login(loginRequest);
    }

}
