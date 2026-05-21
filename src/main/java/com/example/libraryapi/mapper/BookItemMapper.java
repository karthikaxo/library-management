package com.example.libraryapi.mapper;

import com.example.libraryapi.dto.bookItem.BookItemCreateResponse;
import com.example.libraryapi.dto.bookItem.BookItemSearchResponse;
import com.example.libraryapi.dto.bookItem.BookItemUpdate;
import com.example.libraryapi.dto.bookItem.SearchResponseExtra.BookResponse;
import com.example.libraryapi.dto.bookItem.SearchResponseExtra.LendingResponse;
import com.example.libraryapi.dto.bookItem.SearchResponseExtra.LibraryResponse;
import com.example.libraryapi.entity.BookItem;
import com.example.libraryapi.entity.BookItemStatus;
import com.example.libraryapi.entity.Lending;
import com.example.libraryapi.entity.LendingStatus;

import java.util.List;

public class BookItemMapper {
    public static BookItemCreateResponse createToResponse(BookItem bookItem) {
        return BookItemCreateResponse.builder()
                .id(bookItem.getId())
                .isbn(bookItem.getBook().getIsbn())
                .dateAcquired(bookItem.getDateAcquired())
                .address(bookItem.getLibrary().getAddress())
                .barcode(bookItem.getBarcode())
                .build();
    }

    public static BookItemSearchResponse searchToResponse(BookItem bookItem) {
        BookResponse book = BookResponse.builder()
                .title(bookItem.getBook().getTitle())
                .author(bookItem.getBook().getAuthor())
                .isbn(bookItem.getBook().getIsbn())
                .build();

        LibraryResponse library = LibraryResponse.builder()
                .name(bookItem.getLibrary().getName())
                .address(bookItem.getLibrary().getAddress())
                .build();

        LendingResponse currentLending = null;

        if (bookItem.getStatus() == BookItemStatus.UNAVAILABLE ) {
            Lending lending1 = bookItem.getLendings().stream()
                    .filter(lending -> lending.getStatus() == LendingStatus.ACTIVE ||
                            lending.getStatus() == LendingStatus.LOST)
                    .findFirst()
                    .orElse(null);

            if (lending1 != null) {
                currentLending = LendingResponse.builder()
                        .id(lending1.getId())
                        .borrowDate(lending1.getBorrowDate())
                        .dueDate(lending1.getDueDate())
                        .status(lending1.getStatus())
                        .build();
            }
        }

        return BookItemSearchResponse.builder()
                .id(bookItem.getId())
                .book(book)
                .dateAcquired(bookItem.getDateAcquired())
                .library(library)
                .barcode(bookItem.getBarcode())
                .status(bookItem.getStatus())
                .lending(currentLending)
                .build();
    }


    public static List<BookItemSearchResponse> searchToResponseList(List<BookItem> bookItems) {
        return bookItems.stream().map(BookItemMapper::searchToResponse).toList();
    }

    public static BookItemUpdate updateToResponse(BookItem bookItem) {
        return BookItemUpdate.builder()
                .libraryId(bookItem.getLibrary().getId())
                .barcode(bookItem.getBarcode())
                .status(bookItem.getStatus())
                .build();
    }
}
