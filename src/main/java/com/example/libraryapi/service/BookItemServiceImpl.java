package com.example.libraryapi.service;

import com.example.libraryapi.dto.bookItem.BookItemCreateRequest;
import com.example.libraryapi.dto.bookItem.BookItemCreateResponse;
import com.example.libraryapi.dto.bookItem.BookItemSearchResponse;
import com.example.libraryapi.dto.bookItem.BookItemUpdate;
import com.example.libraryapi.entity.Book;
import com.example.libraryapi.entity.BookItem;
import com.example.libraryapi.entity.BookItemStatus;
import com.example.libraryapi.entity.Library;
import com.example.libraryapi.mapper.BookItemMapper;
import com.example.libraryapi.repository.BookItemRepository;
import com.example.libraryapi.repository.BookRepository;
import com.example.libraryapi.repository.LibraryRepository;
import com.example.libraryapi.specification.BookItemSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookItemServiceImpl {

    private final BookItemRepository bookItemRepository;
    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;

    public BookItemServiceImpl(BookItemRepository bookItemRepository,
                               BookRepository bookRepository,
                               LibraryRepository libraryRepository) {
        this.bookItemRepository = bookItemRepository;
        this.bookRepository = bookRepository;
        this.libraryRepository = libraryRepository;
    }

    // ================= CREATE =================

    // Roles: Librarian, Admin
    public BookItemCreateResponse saveBookItem(BookItemCreateRequest bookItemCreateRequest) {
        Book book = bookRepository.findByIsbn(bookItemCreateRequest.getIsbn());
        if (book == null) throw new RuntimeException("This ISBN does not exist.");

        Library library = libraryRepository.findByAddress(bookItemCreateRequest.getAddress());
        if (library == null) throw new RuntimeException("This library address does not exist.");

        if (bookItemRepository.existsByBarcode(bookItemCreateRequest.getBarcode())) {
            throw new RuntimeException("This barcode already exists.");
        }

        BookItem bookItem = new BookItem();
        book.addBookItem(bookItem);
        bookItem.setDateAcquired(LocalDate.now()); // default
        library.addBookItem(bookItem);
        bookItem.setBarcode(bookItemCreateRequest.getBarcode());
        bookItem.setStatus(BookItemStatus.AVAILABLE); // default
        bookItem.setLendings(new ArrayList<>()); // default

        BookItem savedBookItem = bookItemRepository.save(bookItem); // save
        return BookItemMapper.createToResponse(savedBookItem);
    }


    // ================= READ =================

    // Roles: Admin, Librarian
    public BookItemSearchResponse searchBookItemById(Long id) {
        BookItem bookItem = bookItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("This book item ID cannot be found."));

        return BookItemMapper.searchToResponse(bookItem);
    }

    // Roles: Admin, Librarian
    // search by exact barcode
    public BookItemSearchResponse searchBookItemByBarcode(String barcode) {
        BookItem bookItem = bookItemRepository.findByBarcode(barcode);
        if (bookItem == null) { throw new RuntimeException("This book item barcode cannot be found."); }
        return BookItemMapper.searchToResponse(bookItem);
    }

    // Roles: Admin, Librarian
    // search by dateAcquired and status + similar barcodes
    public List<BookItemSearchResponse> searchBookItemBy(BookItemStatus status,
                                                         LocalDate dateAcquired,
                                                         String barcode) {
        Specification<BookItem> spec = (
                root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        if (status != null) {
            spec = spec.and(BookItemSpecification.equalStatus(status));
        }

        if (dateAcquired != null) {
            spec = spec.and(BookItemSpecification.equalDate(dateAcquired));
        }

        if (barcode != null) {
            spec = spec.and(BookItemSpecification.likeBarcode(barcode));
        }

        List<BookItem> bookItems = bookItemRepository.findAll(spec);

        return BookItemMapper.searchToResponseList(bookItems);
    }


    // ================= UPDATE =================

    // Roles: Admin, Librarian
    public BookItemUpdate updateBookItem(Long id, BookItemUpdate bookItemUpdate) {
        BookItem bookItem = bookItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("This book item ID cannot be found."));

        if (bookItemRepository.existsByBarcode(bookItemUpdate.getBarcode())) {
            BookItem existingBookItem = bookItemRepository.findByBarcode(bookItemUpdate.getBarcode());
            if (existingBookItem != null && !existingBookItem.getId().equals(id)) {
                throw new RuntimeException("This barcode already exists.");
            }
        }

        if (bookItemUpdate.getLibraryId() != null) {
            // remove old lib
            if (bookItem.getLibrary() != null) {
                bookItem.getLibrary().removeBookItem(bookItem);
            }
            // set new lib
            Library library = libraryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("This library ID cannot be found."));
            library.addBookItem(bookItem);
        }

        if (bookItemUpdate.getBarcode() != null) {
            bookItem.setBarcode(bookItemUpdate.getBarcode());
        }

        if (bookItemUpdate.getStatus() != null) {
            bookItem.setStatus(bookItemUpdate.getStatus());
        }

        BookItem updatedBookItem = bookItemRepository.save(bookItem);

        return BookItemMapper.updateToResponse(updatedBookItem);
    }


    // ================= DELETE =================

    // Roles: Admin, Librarian
    public void deleteBookItem(Long id) {
        BookItem bookItem = bookItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("This book item ID cannot be found."));
        bookItemRepository.delete(bookItem);
    }


}
