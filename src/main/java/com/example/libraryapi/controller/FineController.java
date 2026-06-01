package com.example.libraryapi.controller;

import com.example.libraryapi.dto.fine.FineResponse;
import com.example.libraryapi.service.FineServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fine")
public class FineController {

    @Autowired
    private FineServiceImpl fineService;

    @PreAuthorize("hasAnyRole('LIBRARIAN','ADMIN')")
    @GetMapping("/search/{id}")
    public FineResponse searchFineById(@PathVariable Long id) {
        return fineService.searchFineById(id);
    }

    @PreAuthorize("hasAnyRole('LIBRARIAN','ADMIN')")
    @PatchMapping("/paid/{id}")
    public FineResponse payFine(@PathVariable Long id) {
        return fineService.payFine(id);
    }
}
