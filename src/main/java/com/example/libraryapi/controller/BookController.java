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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookServiceImpl bookService;


    @PostMapping("/create")
    public BookCreateResponse saveBook(@Valid @RequestBody BookCreateRequest bookCreate) {
        return bookService.saveBook(bookCreate);
    }


    @GetMapping("/search/id/{id}")
    public BookSearchResponse searchBookById(@PathVariable Long id) {
        return bookService.searchBookById(id);
    }

    @GetMapping("/search/isbn/{ISBN}")
    public BookSearchResponse searchBookById(@PathVariable String ISBN) {
        return bookService.searchBookByIsbn(ISBN);
    }

    @GetMapping("/search")
    public List<BookSearchResponse> searchBookBy(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) @Min(1000) @Max(2026) Integer year
    ) {
        return bookService.searchBookBy(title,author,category,year);
    }


    @PatchMapping("/update/{id}")
    public BookUpdate updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookUpdate bookUpdateRequest
    ) {
        return bookService.updateBook(id, bookUpdateRequest);
    }


    @DeleteMapping("/delete/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

}
