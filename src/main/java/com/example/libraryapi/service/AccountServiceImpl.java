package com.example.libraryapi.service;


import com.example.libraryapi.dto.account.LibrarianCreate.LibrarianCreateRequest;
import com.example.libraryapi.dto.account.LibrarianCreate.LibrarianCreateResponse;
import com.example.libraryapi.dto.account.Login.LoginRequest;
import com.example.libraryapi.dto.account.Login.LoginResponse;
import com.example.libraryapi.dto.account.MemberCreate.MemberCreateRequest;
import com.example.libraryapi.dto.account.MemberCreate.MemberCreateResponse;
import com.example.libraryapi.entity.Account;
import com.example.libraryapi.entity.Library;
import com.example.libraryapi.entity.Role;
import com.example.libraryapi.mapper.AccountMapper;
import com.example.libraryapi.repository.AccountRepository;
import com.example.libraryapi.repository.LibraryRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class AccountServiceImpl {

    private final AccountRepository accountRepository;

    private final LibraryRepository libraryRepository;

    private final JWTService jwtService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(7); // strength = 5

    private final AuthenticationManager authManager;

    public AccountServiceImpl(AccountRepository accountRepository, LibraryRepository libraryRepository, JWTService jwtService, AuthenticationManager authManager) {
        this.accountRepository = accountRepository;
        this.libraryRepository = libraryRepository;
        this.jwtService = jwtService;
        this.authManager = authManager;
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
        account.setPassword(encoder.encode(createRequest.getPassword())); // store hashed password
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
        account.setPassword(encoder.encode(createRequest.getPassword()));
        account.setRole(Role.MEMBER);
        account.setCreateDate(LocalDate.now());
        account.setLendings(new ArrayList<>()); // default

        Account savedAccount = accountRepository.save(account);

        return AccountMapper.MemCreateToResponse(savedAccount);
    }


    public LoginResponse login(LoginRequest loginRequest) {

        // authenticate username/password
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                ));

        if (authentication.isAuthenticated()) {
            Account account = accountRepository.findByUsername(loginRequest.getUsername());
            if (account == null) throw new UsernameNotFoundException("User not found.");

            account.setLastLogin(LocalDate.now());
            accountRepository.save(account);

            String token = jwtService.generateToken(account);

            return new LoginResponse(token);
        };

        // login failed
        return new LoginResponse("");
    }
}
