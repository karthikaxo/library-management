package com.example.libraryapi.specification;

import com.example.libraryapi.entity.Account;
import com.example.libraryapi.entity.Role;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class AccountSpecification {

    public static Specification<Account> likeUsername(String username) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(
                                criteriaBuilder.trim(
                                        root.get("username"))),"%" + username.trim().toLowerCase() + "%");
    }

    public static Specification<Account> equalCreateDate(LocalDate createDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("createDate"), createDate);
    }

    public static Specification<Account> equalLastLogin(LocalDate lastLogin) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("lastLogin"), lastLogin);
    }

    // for librarian
    public static Specification<Account> equalLibraryId(Long libraryID) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("library").get("id"), libraryID);
    }

    // roles
    public static Specification<Account> equalMemberRole(Role memberRole) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("role"), memberRole);
    }

    public static Specification<Account> equalLibrarianRole(Role librarianRole) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("role"), librarianRole);
    }
}
