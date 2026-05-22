package com.example.libraryapi.service;


import com.example.libraryapi.dto.account.LibrarianCreate.LibrarianCreateRequest;
import com.example.libraryapi.dto.account.LibrarianCreate.LibrarianCreateResponse;
import com.example.libraryapi.dto.account.MemberCreate.MemberCreateRequest;
import com.example.libraryapi.dto.account.MemberCreate.MemberCreateResponse;
import com.example.libraryapi.entity.Account;
import com.example.libraryapi.entity.Library;
import com.example.libraryapi.entity.Role;
import com.example.libraryapi.mapper.AccountMapper;
import com.example.libraryapi.repository.AccountRepository;
import com.example.libraryapi.repository.LibraryRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class AccountServiceImpl {

    private final AccountRepository accountRepository;

    private final LibraryRepository libraryRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(7); // strength = 5

    public AccountServiceImpl(AccountRepository accountRepository, LibraryRepository libraryRepository) {
        this.accountRepository = accountRepository;
        this.libraryRepository = libraryRepository;
    }

    // ============================== CREATE ==============================

    // Role : Admin
    public LibrarianCreateResponse saveLibrarianAccount(LibrarianCreateRequest createRequest) {

        if (accountRepository.existsByUsername(createRequest.getUsername())) {
            throw new RuntimeException("This username has already been taken.");
        }

        if (accountRepository.existsByEmail(createRequest.getEmail())) {
            throw new RuntimeException("This email has already been taken.");
        }

        Library library = libraryRepository.findById(createRequest.getLibraryID())
                .orElseThrow(() -> new RuntimeException("This library ID cannot be found."));

        Account account = new Account();

        account.setUsername(createRequest.getUsername());
        account.setEmail(createRequest.getEmail());
        account.setPassword(encoder.encode(account.getPassword())); // store hashed password
        account.setRole(Role.LIBRARIAN); // default
        library.addAccount(account);
        account.setCreateDate(LocalDate.now()); // default

        Account savedAccount = accountRepository.save(account);

        return AccountMapper.LibCreateToResponse(savedAccount);
    }

    // No Role check here
    public MemberCreateResponse saveMemberAccount(MemberCreateRequest createRequest) {

        if (accountRepository.existsByUsername(createRequest.getUsername())) {
            throw new RuntimeException("This username has already been taken.");
        }

        if (accountRepository.existsByEmail(createRequest.getEmail())) {
            throw new RuntimeException("This email has already been taken.");
        }

        Account account = new Account();

        account.setUsername(createRequest.getUsername());
        account.setEmail(createRequest.getEmail());
        account.setPassword(encoder.encode(account.getPassword()));
        account.setRole(Role.MEMBER);
        account.setCreateDate(LocalDate.now());
        account.setLendings(new ArrayList<>()); // default

        Account savedAccount = accountRepository.save(account);

        return AccountMapper.MemCreateToResponse(savedAccount);
    }


}
