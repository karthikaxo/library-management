package com.example.libraryapi.specification;

import com.example.libraryapi.entity.Library;
import org.springframework.data.jpa.domain.Specification;

public class LibrarySpecification {

    // similar name
    public static Specification<Library> likeName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(
                                criteriaBuilder.trim(
                                        root.get("name"))), "%" + name.trim().toLowerCase() + "%");
    }

    // exact address
    public static Specification<Library> equalAddress(String address) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        criteriaBuilder.lower(
                                criteriaBuilder.trim(
                                        root.get("address"))), address.trim().toLowerCase());
    }
}
