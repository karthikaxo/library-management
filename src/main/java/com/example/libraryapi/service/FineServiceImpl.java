package com.example.libraryapi.service;

import com.example.libraryapi.dto.fine.FineResponse;
import com.example.libraryapi.entity.Fine;
import com.example.libraryapi.entity.FineStatus;
import com.example.libraryapi.entity.Lending;
import com.example.libraryapi.entity.LendingStatus;
import com.example.libraryapi.mapper.FineMapper;
import com.example.libraryapi.repository.FineRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FineServiceImpl {

    private final FineRepository fineRepository;

    public FineServiceImpl(FineRepository fineRepository) {
        this.fineRepository = fineRepository;
    }

    // ================= CREATE =================

    // called by lostLending in LendingServiceImpl
    public void createFine(Lending lending) {
        // if this lending has already been returned
        if (lending.getStatus() == LendingStatus.RETURNED) {
            throw new RuntimeException("This request is invalid due to the previous status of this lending.");
        }

        Fine fine = new Fine();
        fine.setAmount(20);
        lending.addFine(fine);
        // default
        fine.setCreatedAt(LocalDate.now());
        fine.setStatus(FineStatus.UNPAID);

        fineRepository.save(fine);
    }


    // ================= READ =================

    // Roles: Admin, Librarian
    public FineResponse searchFineById(Long id) {
        Fine fine = fineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("This fine ID cannot be found."));

        return FineMapper.toResponse(fine);
    }


    // ================= UPDATE =================

    // Roles: Admin, Librarian
    public FineResponse payFine(Long id) {

        Fine fine = fineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("This fine ID cannot be found."));

        if (fine.getStatus() == FineStatus.PAID) {
            throw new RuntimeException("Fine is already paid");
        }

        fine.setStatus(FineStatus.PAID);
        Fine updatedFine = fineRepository.save(fine);

        return FineMapper.toResponse(updatedFine);
    }


    // ================= DELETE =================

    // called by returnLending in LendingServiceImpl
    public void deleteFine(Long id) {
        Fine fine = fineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("This fine ID cannot be found."));

        fineRepository.delete(fine);
    }
}
