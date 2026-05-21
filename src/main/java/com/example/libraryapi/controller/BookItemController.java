package com.example.libraryapi.controller;

import com.example.libraryapi.dto.bookItem.BookItemCreateRequest;
import com.example.libraryapi.dto.bookItem.BookItemCreateResponse;
import com.example.libraryapi.dto.bookItem.BookItemSearchResponse;
import com.example.libraryapi.dto.bookItem.BookItemUpdate;
import com.example.libraryapi.entity.BookItemStatus;
import com.example.libraryapi.service.BookItemServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bookitem")
public class BookItemController {

    @Autowired
    private BookItemServiceImpl bookItemService;

    @PostMapping("/create")
    public BookItemCreateResponse saveBookItem(@Valid @RequestBody BookItemCreateRequest createRequest) {
        return bookItemService.saveBookItem(createRequest);
    }

    @GetMapping("/search/id/{id}")
    public BookItemSearchResponse searchBookItemById(@PathVariable Long id) {
        return bookItemService.searchBookItemById(id);
    }

    @GetMapping("/search/barcode/{barcode}")
    public BookItemSearchResponse searchBookItemByBarcode(@PathVariable String barcode) {
        return bookItemService.searchBookItemByBarcode(barcode);
    }

    @GetMapping("/search")
    public List<BookItemSearchResponse> searchBookItemBy(
            @RequestParam(required = false) BookItemStatus status,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) String barcode) {
        return bookItemService.searchBookItemBy(status, date, barcode);
    }

    @PatchMapping("/update/{id}")
    public BookItemUpdate updateBookItem(
            @PathVariable Long id,
            @RequestBody BookItemUpdate bookItemUpdate) {
        return bookItemService.updateBookItem(id, bookItemUpdate);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBookItem(@PathVariable Long id) {
        bookItemService.deleteBookItem(id);
    }
}
