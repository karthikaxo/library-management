package com.example.libraryapi.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int amount;

    @OneToOne
    @JoinColumn(name = "lending_id", nullable = false)
    private Lending lending;

    @Column(nullable = false)
    private int amountPaid;

    @Column(nullable = false)
    private LocalDate createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FineStatus status;
}
