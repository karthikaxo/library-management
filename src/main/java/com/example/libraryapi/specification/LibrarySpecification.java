package com.example.libraryapi.specification;

import com.example.libraryapi.entity.Library;
import org.springframework.data.jpa.domain.Specification;

public class LibrarySpecification {
    public static Specification<Library> likeName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        criteriaBuilder.lower(
                                criteriaBuilder.trim(
                                        root.get("name"))), name.trim().toLowerCase());
    }

    public static Specification<Library> likeAddress(String address) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        criteriaBuilder.lower(
                                criteriaBuilder.trim(
                                        root.get("address"))), address.trim().toLowerCase());
    }
}
