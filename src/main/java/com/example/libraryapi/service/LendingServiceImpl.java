package com.example.libraryapi.service;

import com.example.libraryapi.dto.lending.LendingCreateRequest;
import com.example.libraryapi.dto.lending.LendingResponse;
import com.example.libraryapi.dto.lending.LendingSearchResponse;
import com.example.libraryapi.entity.*;
import com.example.libraryapi.mapper.LendingMapper;
import com.example.libraryapi.repository.AccountRepository;
import com.example.libraryapi.repository.BookItemRepository;
import com.example.libraryapi.repository.LendingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LendingServiceImpl {

    private final LendingRepository lendingRepository;
    private final BookItemRepository bookItemRepository;
    private final AccountRepository accountRepository;

    @Autowired
    private FineServiceImpl fineService;


    public LendingServiceImpl(LendingRepository lendingRepository, BookItemRepository bookItemRepository, AccountRepository accountRepository) {
        this.lendingRepository = lendingRepository;
        this.bookItemRepository = bookItemRepository;
        this.accountRepository = accountRepository;
    }

    // ================= CREATE =================

    // Roles: Admin, Librarian
    public LendingResponse saveLending(LendingCreateRequest lendingRequest) {

        Account account = accountRepository.findById(lendingRequest.getAccountID())
                .orElseThrow(() -> new RuntimeException("This account ID does not exist."));

        BookItem bookItem = bookItemRepository.findByBarcode(lendingRequest.getBarcode());
        if (bookItem == null) throw new RuntimeException("This book item ISBN does not exist.");

        if (lendingRequest.getDueDate().equals(lendingRequest.getBorrowDate())) {
            throw new RuntimeException("The book item cannot be due on the same day it is borrowed.");
        }

        Lending lending = new Lending();
        account.addLending(lending);
        bookItem.addLending(lending);
        lending.setBorrowDate(lendingRequest.getBorrowDate());
        lending.setDueDate(lendingRequest.getDueDate());
        // return date to be set in PATCH
        lending.setStatus(LendingStatus.ACTIVE); // default

        Lending savedLending = lendingRepository.save(lending);

        return LendingMapper.createToResponse(savedLending);
    }


    // ================= READ =================

    // Roles: Admin, Librarian
    // search by ID
    public LendingSearchResponse searchLendingById(Long id) {
        Lending lending = lendingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("This lending ID cannot be found."));

        return LendingMapper.searchToResponse(lending);
    }

    // Roles: Admin, Librarian
    // search by account - optional filters: status
    public List<LendingSearchResponse> searchLendingByMember(Long accountID, String status) {
        Account account = accountRepository
                .findByIdAndRole(accountID, Role.MEMBER);
        if (account == null) throw new RuntimeException("This member ID cannot be found.");

        List<Lending> lendings;

        if (status == null) {
            // no status filter
            lendings = lendingRepository.findByAccount(account);
        } else {
            LendingStatus lendingStatus;

            try {
                // valid status filter
                lendingStatus = LendingStatus.valueOf(status.toUpperCase());

            } catch (IllegalArgumentException e) { throw new RuntimeException("Invalid lending status."); }

            lendings = lendingRepository.findByAccountAndStatus(account, lendingStatus);
        }

        return LendingMapper.searchToResponseList(lendings);
    }

    // Roles: Admin, Librarian
    // all overdue books
    public List<LendingSearchResponse> searchOverdueLending() {
        List<Lending> overdueLendings = lendingRepository
                .findByDueDateBeforeAndStatus(LocalDate.now(), LendingStatus.ACTIVE);

        return LendingMapper.searchToResponseList(overdueLendings);
    }


    // ================= UPDATE =================

    // Roles: Admin, Librarian
    public LendingResponse returnLending(Long id) {
        Lending lending = lendingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("This lending ID cannot be found."));

        // if lending is previously LOST and is now RETURNED, then delete Fine associated with lending
        if (lending.getStatus() == LendingStatus.LOST) {
            fineService.deleteFine(lending.getFine().getId());
            lending.setFine(null);
        }

        lending.setReturnDate(LocalDate.now());
        lending.setStatus(LendingStatus.RETURNED);
        Lending updatedLending = lendingRepository.save(lending);

        return LendingMapper.createToResponse(updatedLending);
    }

    // Roles: Admin, Librarian
    public LendingResponse lostLending(Long id) {
        Lending lending = lendingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("This lending ID cannot be found."));

        // if this lending has already been returned
        if (lending.getStatus() == LendingStatus.RETURNED) {
            throw new RuntimeException("This lending cannot be set to LOST as the book has already been returned.");
        }

        // if a Fine has already been set with status PAID
        if (lending.getFine().getStatus() == FineStatus.PAID) {
            throw new RuntimeException("This lending is already lost and the fine has already been paid off.");
        }

        lending.setStatus(LendingStatus.LOST);
        Lending updatedLending = lendingRepository.save(lending);

        // set Fine
        fineService.createFine(updatedLending);

        return LendingMapper.createToResponse(updatedLending);
    }


    // ================= DELETE =================

    // Roles: Admin, Librarian
    public void deleteLending(Long id) {
        Lending lending = lendingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("This lending ID cannot be found."));

        lendingRepository.delete(lending);
    }






}
