package com.example.libraryapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Data // getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) // store as string
    @Column(nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "library_id") // nullable true as 'MEMBERS' don't require library
    private Library library; // only for librarian role

    @Column(nullable = false)
    private LocalDate createDate;

    @Column(nullable = false)
    private LocalDate lastLogin;

    @OneToMany(mappedBy = "account")
    private List<Lending> lendings; // books borrowed, returned etc
}
