package com.example.libraryapi.specification;

import com.example.libraryapi.entity.BookItem;
import com.example.libraryapi.entity.BookItemStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class BookItemSpecification {

    // exact status
    public static Specification<BookItem> equalStatus(BookItemStatus status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("status"), status);
    }

    // exact date
    public static Specification<BookItem> equalDate(LocalDate date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("dateAcquired"), date);
    }

    // similar barcodes
    public static Specification<BookItem> likeBarcode(String barcode) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(
                                criteriaBuilder.trim(
                                        root.get("barcode"))), "%" + barcode.trim().toLowerCase() + "%");
    }
}
