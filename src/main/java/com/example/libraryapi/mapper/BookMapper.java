package com.example.libraryapi.mapper;

import com.example.libraryapi.dto.book.BookCreateResponse;
import com.example.libraryapi.dto.book.BookSearchResponse;
import com.example.libraryapi.dto.book.BookUpdate;
import com.example.libraryapi.entity.Book;
import com.example.libraryapi.entity.BookItemStatus;

import java.util.List;

public class BookMapper {

    public static BookCreateResponse createToResponse(Book book) {
        return BookCreateResponse.builder()
                .id(book.getId())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .author(book.getAuthor())
                .category(book.getCategory())
                .yearPublished(book.getYearPublished())
                .build();
    }

    public static BookSearchResponse searchToResponse(Book book) {

        boolean available = book.getCopies().stream()
                .anyMatch(bookItem -> bookItem.getStatus() == BookItemStatus.AVAILABLE);

        List<String> locations = book.getCopies().stream()
                .filter(bookItem -> bookItem.getStatus() == BookItemStatus.AVAILABLE)
                .map(bookItem -> bookItem.getLibrary().getName())
                .distinct()
                .toList();

        return BookSearchResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .category(book.getCategory())
                .yearPublished(book.getYearPublished())
                .available(available)
                .locations(locations)
                .build();
    }

    public static List<BookSearchResponse> searchToResponseList(List<Book> books) {
        return (books
                .stream().map(BookMapper::searchToResponse)
                .toList());
    }

    public static BookUpdate updateToResponse(Book book) {
        return BookUpdate.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .author(book.getAuthor())
                .category(book.getCategory())
                .yearPublished(book.getYearPublished())
                .build();
    }

}
