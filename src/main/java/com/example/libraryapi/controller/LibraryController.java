package com.example.libraryapi.controller;

import com.example.libraryapi.dto.library.LibraryCreateRequest;
import com.example.libraryapi.dto.library.LibraryCreateResponse;
import com.example.libraryapi.dto.library.LibrarySearchResponse;
import com.example.libraryapi.dto.library.LibraryUpdate;
import com.example.libraryapi.service.LibraryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    private LibraryServiceImpl libraryService;


    @PostMapping("/create")
    public LibraryCreateResponse saveLibrary(@Valid @RequestBody LibraryCreateRequest createRequest) {
        return libraryService.saveLibrary(createRequest);
    }

    @GetMapping("/search/{id}")
    public LibrarySearchResponse searchLibraryById(@PathVariable Long id) {
        return libraryService.searchLibraryById(id);
    }

    @GetMapping("/search")
    public List<LibrarySearchResponse> searchLibraryBy(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address) {
        return libraryService.searchLibraryBy(name, address);
    }

    @PatchMapping("/update/{id}")
    public LibraryUpdate updateLibrary(
            @PathVariable Long id,
            @RequestBody LibraryUpdate libraryUpdate) {
        return libraryService.updateLibrary(id, libraryUpdate);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteLibrary(@PathVariable Long id) {
        libraryService.deleteLibrary(id);
    }
}
