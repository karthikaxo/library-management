package com.example.libraryapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String address;

    @OneToMany(mappedBy = "library", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<BookItem> inventory; // book items in a library

    @OneToMany(mappedBy = "library", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Account> accounts; // 'LIBRARIAN'(s) in a specific library

    // helper methods

    // used in BookItemServiceImpl
    public void addBookItem(BookItem bookItem) {
        inventory.add(bookItem);
        bookItem.setLibrary(this);
    }

    public void removeBookItem(BookItem bookItem) {
        inventory.remove(bookItem);
        bookItem.setLibrary(null);
    }

    // used in AccountServiceImpl
    public void addAccount(Account account) {
        accounts.add(account);
        account.setLibrary(this);
    }

    // used in AccountServiceImpl
    public void removeAccount(Account account) {
        accounts.remove(account);
        account.setLibrary(null);
    }
}
