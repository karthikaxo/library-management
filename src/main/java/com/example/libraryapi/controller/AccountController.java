package com.example.libraryapi.controller;

import com.example.libraryapi.dto.account.LibrarianCreate.LibrarianCreateRequest;
import com.example.libraryapi.dto.account.LibrarianCreate.LibrarianCreateResponse;
import com.example.libraryapi.dto.account.Login.LoginRequest;
import com.example.libraryapi.dto.account.Login.LoginResponse;
import com.example.libraryapi.dto.account.MemberCreate.MemberCreateRequest;
import com.example.libraryapi.dto.account.MemberCreate.MemberCreateResponse;
import com.example.libraryapi.dto.account.SearchResponse.LibrarianSearchResponse;
import com.example.libraryapi.dto.account.SearchResponse.MemberSearchResponse;
import com.example.libraryapi.dto.account.Update.LibrarianUpdate;
import com.example.libraryapi.dto.account.Update.MemberUpdate;
import com.example.libraryapi.dto.account.Update.PasswordUpdate;
import com.example.libraryapi.service.AccountServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    // no auth
    @PostMapping("/create-mem")
    public MemberCreateResponse saveMemberAccount(@Valid @RequestBody MemberCreateRequest createRequest) {
        return accountService.saveMemberAccount(createRequest);
    }

    // no auth
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return accountService.login(loginRequest);
    }



    @PreAuthorize("hasAnyRole('LIBRARIAN','ADMIN')")
    @GetMapping("/search-mem/id/{id}")
    public MemberSearchResponse searchMemById(@PathVariable Long id) {
        return accountService.searchMemById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/search-lib/id/{id}")
    public LibrarianSearchResponse searchLibById(@PathVariable Long id) {
        return accountService.searchLibById(id);
    }

    @PreAuthorize("hasAnyRole('LIBRARIAN','ADMIN')")
    @GetMapping("/search-mem/username/{username}")
    public MemberSearchResponse searchMemByUsername(@PathVariable String username) {
        return accountService.searchMemByUsername(username);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/search-lib/username/{username}")
    public LibrarianSearchResponse searchLibByUsername(@PathVariable String username) {
        return accountService.searchLibByUsername(username);
    }

    @PreAuthorize("hasAnyRole('LIBRARIAN','ADMIN')")
    @GetMapping("/search-mem/email/{email}")
    public MemberSearchResponse searchMemByEmail(@PathVariable String email) {
        return accountService.searchMemByEmail(email);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/search-lib/email/{email}")
    public LibrarianSearchResponse searchLibByEmail(@PathVariable String email) {
        return accountService.searchLibByEmail(email);
    }

    @PreAuthorize("hasAnyRole('LIBRARIAN','ADMIN')")
    @GetMapping("/search-mem")
    public List<MemberSearchResponse> searchMemBy(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) LocalDate createDate,
            @RequestParam(required = false) LocalDate lastLogin) {
        return accountService.searchMemBy(username, createDate, lastLogin);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/search-lib")
    public List<LibrarianSearchResponse> searchLibBy(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) @Min(1) Long libraryID,
            @RequestParam(required = false) LocalDate createDate,
            @RequestParam(required = false) LocalDate lastLogin) {
        return accountService.searchLibBy(username, libraryID, createDate, lastLogin);
    }


    @PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
    @PatchMapping("/update-mem/{id}")
    public MemberUpdate updateMember(@PathVariable Long id,
                                     @Valid @RequestBody MemberUpdate memberUpdate) {
        return accountService.updateMember(id, memberUpdate);
    }

    @PreAuthorize("hasAnyRole('LIBRARIAN','ADMIN')")
    @PatchMapping("/update-lib/{id}")
    public LibrarianUpdate updateLibrarian(@PathVariable Long id,
                                           @Valid @RequestBody LibrarianUpdate librarianUpdate) {
        return accountService.updateLibrarian(id, librarianUpdate);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
    @PatchMapping("/update-pw-mem/{id}")
    public String updateMemPassword(@PathVariable Long id,
                                    @Valid @RequestBody PasswordUpdate passwordUpdate) {
        return accountService.updateMemPassword(id, passwordUpdate);
    }

    @PreAuthorize("hasAnyRole('LIBRARIAN','ADMIN')")
    @PatchMapping("/update-pw-lib/{id}")
    public String updateLibPassword(@PathVariable Long id,
                                    @Valid @RequestBody PasswordUpdate passwordUpdate) {
        return accountService.updateLibPassword(id, passwordUpdate);
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }



}
