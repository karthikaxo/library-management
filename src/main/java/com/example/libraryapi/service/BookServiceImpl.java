package com.example.libraryapi.service;

import com.example.libraryapi.dto.book.BookCreateRequest;
import com.example.libraryapi.dto.book.BookCreateResponse;
import com.example.libraryapi.dto.book.BookSearchResponse;
import com.example.libraryapi.dto.book.BookUpdate;
import com.example.libraryapi.entity.Book;
import com.example.libraryapi.mapper.BookMapper;
import com.example.libraryapi.repository.BookRepository;
import com.example.libraryapi.specification.BookSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // ====================== CREATE ======================

    // Roles: Librarian, Admin
    public BookCreateResponse saveBook(BookCreateRequest bookDetails) {
        Book book = new Book();

        if (bookRepository.existsByIsbn(bookDetails.getIsbn())) {
            throw new RuntimeException("ISBN already exists.");
        }
        book.setIsbn(bookDetails.getIsbn());
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setCategory(bookDetails.getCategory());
        book.setYearPublished(bookDetails.getYearPublished());
        // copies is empty upon creation
        book.setCopies(new ArrayList<>());

        // save book
        Book savedBook = bookRepository.save(book);

        // convert to dto
        return BookMapper.createToResponse(savedBook);
    }


    // ====================== READ ======================

    // Roles: ALL
    // search for a specific book
    public BookSearchResponse searchBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("This book ID cannot be found."));

        // convert to dto
        return BookMapper.searchToResponse(book);
    }

    // Roles: ALL
    // search for books through filters
    public List<BookSearchResponse> searchBooksBy(String title,
                                                  String author,
                                                  String category,
                                                  Integer year) {
        // chaining specifications
        Specification<Book> spec =
                (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        if (title != null) {
            spec = spec.and(BookSpecification.likeTitle(title));
        }
        if (author != null) {
            spec = spec.and(BookSpecification.likeAuthor(author));
        }
        if (category != null) {
            spec = spec.and(BookSpecification.likeCategory(category));
        }
        if (year != null && year >= 1000 && year <= 2026) spec = spec.and(BookSpecification.likeYear(year));

        List<Book> books =  bookRepository.findAll(spec);

        // convert to dto
        return BookMapper.searchToResponseList(books);
    }


    // ====================== UPDATE ======================

    // Roles: Librarian, Admin
    // PATCH
    public BookUpdate updateBook(Long id, BookUpdate bookUpdateRequest) {
        Book updatedBook = bookRepository.findById(id).orElseThrow(
                () -> new RuntimeException("This book ID cannot be found."));

        // only updating metadata (partially or fully)

        if (bookUpdateRequest.getIsbn() != null) {
            Book existingBook = bookRepository.findByIsbn(bookUpdateRequest.getIsbn());
            // if the ISBN of the existing book is not the current book being updated, then error
            if (existingBook != null && !existingBook.getId().equals(id)) {
                throw new RuntimeException("ISBN already exists.");
            }
            updatedBook.setIsbn(bookUpdateRequest.getIsbn());
        }

        if (bookUpdateRequest.getTitle() != null) {
            updatedBook.setTitle(bookUpdateRequest.getTitle());
        }

        if (bookUpdateRequest.getAuthor() != null) {
            updatedBook.setAuthor(bookUpdateRequest.getAuthor());
        }

        if (bookUpdateRequest.getCategory() != null) {
            updatedBook.setCategory(bookUpdateRequest.getCategory());
        }

        if (bookUpdateRequest.getYearPublished() != null &&
                bookUpdateRequest.getYearPublished() >= 1000 &&
                bookUpdateRequest.getYearPublished() <= 2026) {
            updatedBook.setYearPublished(bookUpdateRequest.getYearPublished());
        }

        Book book = bookRepository.save(updatedBook);

        return BookMapper.updateToResponse(book);
    }


    // ====================== DELETE ======================

    // Roles: Librarian, Admin
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("This book ID cannot be found."));
        bookRepository.delete(book);
    }

}
