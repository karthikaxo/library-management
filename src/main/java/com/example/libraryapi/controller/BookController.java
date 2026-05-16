package com.example.libraryapi.controller;

import com.example.libraryapi.dto.book.BookCreateRequest;
import com.example.libraryapi.dto.book.BookCreateResponse;
import com.example.libraryapi.service.BookServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookServiceImpl bookService;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public BookCreateResponse create(@Valid @RequestBody BookCreateRequest bookCreate) {
        return bookService.saveBook(bookCreate);
    }


}
