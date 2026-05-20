package com.example.libraryapi.mapper;

import com.example.libraryapi.dto.library.LibraryCreateResponse;
import com.example.libraryapi.dto.library.LibrarySearchResponse;
import com.example.libraryapi.dto.library.LibraryUpdate;
import com.example.libraryapi.dto.library.SubResponse.InventoryResponse;
import com.example.libraryapi.dto.library.SubResponse.LibrarianResponse;
import com.example.libraryapi.entity.BookItem;
import com.example.libraryapi.entity.Library;

import java.util.List;
import java.util.stream.Collectors;

public class LibraryMapper {

    public static LibraryCreateResponse createToResponse(Library library) {
        return LibraryCreateResponse.builder()
                .id(library.getId())
                .name(library.getName())
                .address(library.getAddress())
                .build();
    }

    // name, address
    // inventory
    public static LibrarySearchResponse searchToResponse(Library library) {

        // account: id, username, email, role
        List<LibrarianResponse> accounts = library.getAccounts().stream()
                .map(account -> LibrarianResponse.builder()
                        .id(account.getId())
                        .username(account.getUsername())
                        .email(account.getEmail())
                        .role(account.getRole())
                        .build())
                .toList();

        // inventory : Book id, Book title, No. of book items for this Book within this library
        List<InventoryResponse> inventory = library.getInventory().stream()
                // key: Book ; val: list of BookItem
                .collect(Collectors.groupingBy(BookItem::getBook)) // Book -> [BookItem1, BookItem2, ...]
                .entrySet().stream()
                .map(bookListEntry -> InventoryResponse.builder()
                        .id(bookListEntry.getKey().getId())
                        .title(bookListEntry.getKey().getTitle())
                        .copies(bookListEntry.getValue().size()) // size of this list, not size of copies under Book
                        .build())
                .toList();

        // librarian : account with role librarian, find name, email. id
        return LibrarySearchResponse.builder()
                .id(library.getId())
                .name(library.getName())
                .address(library.getAddress())
                .inventory(inventory)
                .accounts(accounts)
                .build();
    }

    public static List<LibrarySearchResponse> searchToResponseList(List<Library> libraries) {
        return libraries.stream().map(LibraryMapper::searchToResponse).toList();
    }

    public static LibraryUpdate updateToResponse(Library library) {
        return LibraryUpdate.builder()
                .name(library.getName())
                .address(library.getAddress())
                .build();
    }
}
