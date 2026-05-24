package com.example.libraryapi.controller;

import com.example.libraryapi.dto.book.BookCreateRequest;
import com.example.libraryapi.dto.book.BookCreateResponse;
import com.example.libraryapi.dto.book.BookSearchResponse;
import com.example.libraryapi.dto.book.BookUpdate;
import com.example.libraryapi.service.BookServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookServiceImpl bookService;


    @PreAuthorize("hasAnyRole('LIBRARIAN','ADMIN')")
    @PostMapping("/create")
    public BookCreateResponse saveBook(@Valid @RequestBody BookCreateRequest bookCreate) {
        return bookService.saveBook(bookCreate);
    }


    @PreAuthorize("hasAnyRole('MEMBER','LIBRARIAN','ADMIN')")
    @GetMapping("/search/id/{id}")
    public BookSearchResponse searchBookById(@PathVariable Long id) {
        return bookService.searchBookById(id);
    }

    @PreAuthorize("hasAnyRole('MEMBER','LIBRARIAN','ADMIN')")
    @GetMapping("/search/isbn/{ISBN}")
    public BookSearchResponse searchBookByIsbn(@PathVariable String ISBN) {
        return bookService.searchBookByIsbn(ISBN);
    }

    @PreAuthorize("hasAnyRole('MEMBER','LIBRARIAN','ADMIN')")
    @GetMapping("/search")
    public List<BookSearchResponse> searchBookBy(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) @Min(1000) @Max(2026) Integer year
    ) {
        return bookService.searchBookBy(title,author,category,year);
    }


    @PreAuthorize("hasAnyRole('LIBRARIAN','ADMIN')")
    @PatchMapping("/update/{id}")
    public BookUpdate updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookUpdate bookUpdateRequest
    ) {
        return bookService.updateBook(id, bookUpdateRequest);
    }


    @PreAuthorize("hasAnyRole('LIBRARIAN','ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

}
