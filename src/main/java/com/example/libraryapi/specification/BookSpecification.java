package com.example.libraryapi.specification;

import com.example.libraryapi.entity.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {
    public static Specification<Book> likeTitle(String title) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
        // within col named 'title', look for pattern with given string title
    }

    public static Specification<Book> likeAuthor(String author) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("author"), "%" + author + "%");
    }

    public static Specification<Book> likeCategory(String category) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("category"), "%" + category + "%");
    }

    public static Specification<Book> likeYear(int year) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("yearPublished"), year);
        // specific year
    }
}
