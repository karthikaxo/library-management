package com.example.libraryapi.service;

import com.example.libraryapi.dto.library.LibraryCreateRequest;
import com.example.libraryapi.dto.library.LibraryCreateResponse;
import com.example.libraryapi.dto.library.LibrarySearchResponse;
import com.example.libraryapi.dto.library.LibraryUpdate;
import com.example.libraryapi.entity.Library;
import com.example.libraryapi.mapper.LibraryMapper;
import com.example.libraryapi.repository.LibraryRepository;
import com.example.libraryapi.specification.LibrarySpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryServiceImpl {

    private final LibraryRepository libraryRepository;

    public LibraryServiceImpl(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    // ====================== CREATE ======================

    // Roles: Admin
    public LibraryCreateResponse saveLibrary(LibraryCreateRequest libraryCreateRequest) {
        Library library = new Library();

        if (libraryRepository.existsByAddress(libraryCreateRequest.getAddress())) {
            throw new RuntimeException("Address already exists.");
        }
        library.setAddress(libraryCreateRequest.getAddress());
        library.setName(libraryCreateRequest.getName());
        library.setInventory(new ArrayList<>()); // empty inventory and accounts
        library.setAccounts(new ArrayList<>());

        // save
        Library savedLibrary = libraryRepository.save(library);

        // dto response
        return LibraryMapper.createToResponse(savedLibrary);
    }


    // ====================== READ ======================

    // Roles: Admin
    // search for a specific library by ID
    public LibrarySearchResponse searchLibraryById(Long id) {
        Library library = libraryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("This library ID cannot be found."));

        return LibraryMapper.searchToResponse(library);
    }

    // Roles: Admin
    // filter through libraries address and similar names
    public List<LibrarySearchResponse> searchLibraryBy(String name, String address) {
        Specification<Library> spec = ((root, query, criteriaBuilder)
                -> criteriaBuilder.conjunction());
        if (name != null) {
            spec = spec.and(LibrarySpecification.likeName(name));
        }
        if (address != null) {
            spec = spec.and(LibrarySpecification.equalAddress(address));
        }
        List<Library> libraries = libraryRepository.findAll(spec);

        return LibraryMapper.searchToResponseList(libraries);
    }


    // ====================== UPDATE ======================

    // Roles: Admin
    // PATCH
    public LibraryUpdate updateLibrary(Long id, LibraryUpdate libraryUpdate) {
        Library library = libraryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("This library ID cannot be found."));

        if (libraryUpdate.getAddress() != null) {
            Library existingLibrary = libraryRepository.findByAddress(libraryUpdate.getAddress());
            // if not current library
            if (existingLibrary != null && !existingLibrary.getId().equals(id)) {
                throw new RuntimeException("Address already exists.");
            }
            library.setAddress(libraryUpdate.getAddress());
        }

        if (libraryUpdate.getName() != null) {
            library.setName(libraryUpdate.getName());
        }

        Library updatedLibrary = libraryRepository.save(library);

        return LibraryMapper.updateToResponse(updatedLibrary);
    }


    // ====================== DELETE ======================

    // Roles: Admin
    public void deleteLibrary(Long id) {
        Library library = libraryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("This library ID cannot be found."));
        libraryRepository.delete(library);
    }
}
