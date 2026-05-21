package com.example.libraryapi.specification;

import com.example.libraryapi.entity.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    // exact title
    public static Specification<Book> equalTitle(String title) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        criteriaBuilder.lower(
                                criteriaBuilder.trim(
                                    root.get("title"))), title.trim().toLowerCase());
    }

    // exact author
    public static Specification<Book> equalAuthor(String author) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        criteriaBuilder.lower(
                                criteriaBuilder.trim(
                                        root.get("author"))), author.trim().toLowerCase());
    }

    // exact category
    public static Specification<Book> equalCategory(String category) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        criteriaBuilder.lower(
                                criteriaBuilder.trim(
                                        root.get("category"))), category.trim().toLowerCase());
    }

    // exact year
    public static Specification<Book> equalYear(int year) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("yearPublished"), year);
    }
}
