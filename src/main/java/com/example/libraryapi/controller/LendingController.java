package com.example.libraryapi.controller;

import com.example.libraryapi.dto.lending.LendingCreateRequest;
import com.example.libraryapi.dto.lending.LendingResponse;
import com.example.libraryapi.dto.lending.LendingSearchResponse;
import com.example.libraryapi.service.LendingServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lending")
public class LendingController {

    @Autowired
    private LendingServiceImpl lendingService;

    @PreAuthorize("hasAnyRole('LIBRARIAN','ADMIN')")
    @PostMapping("/create")
    public LendingResponse saveLending(@Valid @RequestBody LendingCreateRequest lendingRequest) {
        return lendingService.saveLending(lendingRequest);
    }

    @PreAuthorize("hasAnyRole('LIBRARIAN','ADMIN')")
    @GetMapping("/search/id/{id}")
    public LendingSearchResponse searchLendingById(@PathVariable Long id) {
        return lendingService.searchLendingById(id);
    }

    @PreAuthorize("hasAnyRole('LIBRARIAN','ADMIN')")
    @GetMapping("/search/accountID/{accountID}")
    public List<LendingSearchResponse> searchLendingByMember(
            @PathVariable Long accountID,
            @RequestParam(required = false) String status) {
        return lendingService.searchLendingByMember(accountID,status);
    }

    @PreAuthorize("hasAnyRole('LIBRARIAN','ADMIN')")
    @GetMapping("/search/overdue")
    public List<LendingSearchResponse> searchOverdueLending() {
        return lendingService.searchOverdueLending();
    }

    @PreAuthorize("hasAnyRole('LIBRARIAN','ADMIN')")
    @PatchMapping("/return/{id}")
    public LendingResponse returnLending(@PathVariable Long id) {
        return lendingService.returnLending(id);
    }

    @PreAuthorize("hasAnyRole('LIBRARIAN','ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void deleteLending(@PathVariable Long id) {
        lendingService.deleteLending(id);
    }


}
