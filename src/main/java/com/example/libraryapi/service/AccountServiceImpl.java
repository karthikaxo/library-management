package com.example.libraryapi.service;


import com.example.libraryapi.dto.account.SearchResponse.LibrarianSearchResponse;
import com.example.libraryapi.dto.account.SearchResponse.MemberSearchResponse;
import com.example.libraryapi.dto.account.LibrarianCreate.LibrarianCreateRequest;
import com.example.libraryapi.dto.account.LibrarianCreate.LibrarianCreateResponse;
import com.example.libraryapi.dto.account.Login.LoginRequest;
import com.example.libraryapi.dto.account.Login.LoginResponse;
import com.example.libraryapi.dto.account.MemberCreate.MemberCreateRequest;
import com.example.libraryapi.dto.account.MemberCreate.MemberCreateResponse;
import com.example.libraryapi.dto.account.Update.LibrarianUpdate;
import com.example.libraryapi.dto.account.Update.MemberUpdate;
import com.example.libraryapi.dto.account.Update.PasswordUpdate;
import com.example.libraryapi.entity.Account;
import com.example.libraryapi.entity.LendingStatus;
import com.example.libraryapi.entity.Library;
import com.example.libraryapi.entity.Role;
import com.example.libraryapi.mapper.AccountMapper;
import com.example.libraryapi.repository.AccountRepository;
import com.example.libraryapi.repository.LibraryRepository;
import com.example.libraryapi.specification.AccountSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl {

    private final AccountRepository accountRepository;

    private final LibraryRepository libraryRepository;

    private final JWTService jwtService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(7); // strength = 5

    private final AuthenticationManager authManager;

    public AccountServiceImpl(AccountRepository accountRepository, LibraryRepository libraryRepository, JWTService jwtService, AuthenticationManager authManager) {
        this.accountRepository = accountRepository;
        this.libraryRepository = libraryRepository;
        this.jwtService = jwtService;
        this.authManager = authManager;
    }


    // ============================== CREATE ==============================

    // Role : Admin
    public LibrarianCreateResponse saveLibrarianAccount(LibrarianCreateRequest createRequest) {

        if (accountRepository.existsByUsername(createRequest.getUsername())) {
            throw new RuntimeException("This username has already been taken.");
        }

        if (accountRepository.existsByEmail(createRequest.getEmail())) {
            throw new RuntimeException("This email has already been taken.");
        }

        Library library = libraryRepository.findById(createRequest.getLibraryID())
                .orElseThrow(() -> new RuntimeException("This library ID cannot be found."));

        Account account = new Account();

        account.setUsername(createRequest.getUsername());
        account.setEmail(createRequest.getEmail());
        account.setPassword(encoder.encode(createRequest.getPassword())); // store hashed password
        account.setRole(Role.LIBRARIAN); // default
        library.addAccount(account);
        account.setCreateDate(LocalDate.now()); // default

        Account savedAccount = accountRepository.save(account);

        return AccountMapper.LibCreateToResponse(savedAccount);
    }

    // No Role check here
    public MemberCreateResponse saveMemberAccount(MemberCreateRequest createRequest) {

        if (accountRepository.existsByUsername(createRequest.getUsername())) {
            throw new RuntimeException("This username has already been taken.");
        }

        if (accountRepository.existsByEmail(createRequest.getEmail())) {
            throw new RuntimeException("This email has already been taken.");
        }

        Account account = new Account();

        account.setUsername(createRequest.getUsername());
        account.setEmail(createRequest.getEmail());
        account.setPassword(encoder.encode(createRequest.getPassword()));
        account.setRole(Role.MEMBER);
        account.setCreateDate(LocalDate.now());
        account.setLendings(new ArrayList<>()); // default

        Account savedAccount = accountRepository.save(account);

        return AccountMapper.MemCreateToResponse(savedAccount);
    }

    // No Role check here
    public LoginResponse login(LoginRequest loginRequest) {

        // authenticate username/password
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                ));

        if (authentication.isAuthenticated()) {
            Account account = accountRepository.findByUsername(loginRequest.getUsername());
            if (account == null) throw new UsernameNotFoundException("User not found.");

            account.setLastLogin(LocalDate.now());
            accountRepository.save(account);

            String token = jwtService.generateToken(account);

            return new LoginResponse(token);
        };

        // login failed
        return new LoginResponse("");
    }


    // ============================== READ ==============================


    //           ------------------ searchById ------------------

    // Role: Librarian, Admin
    public MemberSearchResponse searchMemById(Long id) {
        Account account = accountRepository
                .findByIdAndRole(id, Role.MEMBER);
        if (account == null) throw new RuntimeException("This member ID cannot be found.");

        // dto
        return AccountMapper.searchMemToResponse(account);
    }

    // Role: Admin
    public LibrarianSearchResponse searchLibById(Long id) {
        Account account = accountRepository
                .findByIdAndRole(id, Role.LIBRARIAN);
        if (account == null) throw new RuntimeException("This librarian ID cannot be found.");

        return AccountMapper.searchLibToResponse(account);
    }


    //           ------------------ searchByUsername ------------------

    // Role: Librarian, Admin
    public MemberSearchResponse searchMemByUsername(String username) {
        Account account = accountRepository
                .findByUsernameAndRole(username, Role.MEMBER);
        if (account == null) throw new RuntimeException("This member username cannot be found.");

        // dto
        return AccountMapper.searchMemToResponse(account);
    }

    // Role: Admin
    public LibrarianSearchResponse searchLibByUsername(String username) {
        Account account = accountRepository
                .findByUsernameAndRole(username, Role.LIBRARIAN);
        if (account == null) throw new RuntimeException("This librarian username cannot be found.");

        // dto
        return AccountMapper.searchLibToResponse(account);
    }


    //           ------------------ searchByEmail ------------------

    // Role: Librarian, Admin
    public MemberSearchResponse searchMemByEmail(String email) {
        Account account = accountRepository
                .findByEmailAndRole(email, Role.MEMBER);
        if (account == null) throw new RuntimeException("This member email cannot be found.");

        // dto
        return AccountMapper.searchMemToResponse(account);
    }

    // Role: Admin
    public LibrarianSearchResponse searchLibByEmail(String email) {
        Account account = accountRepository
                .findByEmailAndRole(email, Role.LIBRARIAN);
        if (account == null) throw new RuntimeException("This librarian email cannot be found.");

        // dto
        return AccountMapper.searchLibToResponse(account);
    }


    //           ------------------ searchBy ------------------

    // Role: Librarian, Admin
    // filters : CreateDate, LastLogin, username (pattern)
    public List<MemberSearchResponse> searchMemBy(String username,
                                                  LocalDate createDate,
                                                  LocalDate lastLogin) {
        Specification<Account> spec = (root, query, criteriaBuilder) ->
                criteriaBuilder.conjunction();

        spec = spec.and(AccountSpecification.equalMemberRole(Role.MEMBER));

        if (username != null) {
            spec = spec.and(AccountSpecification.likeUsername(username));
        }

        if (createDate != null) {
            spec = spec.and(AccountSpecification.equalCreateDate(createDate));
        }

        if (lastLogin != null) {
            spec = spec.and(AccountSpecification.equalLastLogin(lastLogin));
        }

        List<Account> accounts = accountRepository.findAll(spec);

        return AccountMapper.searchMemToResponseList(accounts);

    }


    // Role: Admin
    // filters : CreateDate, LastLogin, LibraryID, username (pattern)
    public List<LibrarianSearchResponse> searchLibBy(String username,
                                                      Long libraryID,
                                                      LocalDate createDate,
                                                      LocalDate lastLogin) {
        Specification<Account> spec = (root, query, criteriaBuilder) ->
                criteriaBuilder.conjunction();

        spec = spec.and(AccountSpecification.equalLibrarianRole(Role.LIBRARIAN));

        if (username != null) {
            spec = spec.and(AccountSpecification.likeUsername(username));
        }

        if (libraryID != null) {
            spec = spec.and(AccountSpecification.equalLibraryId(libraryID));
        }

        if (createDate != null) {
            spec = spec.and(AccountSpecification.equalCreateDate(createDate));
        }

        if (lastLogin != null) {
            spec = spec.and(AccountSpecification.equalLastLogin(lastLogin));
        }

        List<Account> accounts = accountRepository.findAll(spec);

        return AccountMapper.searchLibToResponseList(accounts);
    }


    //           ------------------ update ------------------

    // Role: Member, Admin
    // updates: username, email
    public MemberUpdate updateMember(Long id, MemberUpdate memberUpdate) {
        Account account = accountRepository
                .findByIdAndRole(id, Role.MEMBER);
        if (account == null) throw new RuntimeException("This member ID cannot be found.");

        if (memberUpdate.getUsername() != null) {
            validateAndSetUsername(id, account, memberUpdate.getUsername());
        }

        if (memberUpdate.getEmail() != null) {
            validateAndSetEmail(id, account, memberUpdate.getEmail());
        }

        Account updatedAccount = accountRepository.save(account);

        return AccountMapper.updateMemToResponse(updatedAccount);
    }

    // Role: Librarian, Admin
    // updates: library, username, email
    public LibrarianUpdate updateLibrarian(Long id, LibrarianUpdate librarianUpdate) {
        Account account = accountRepository
                .findByIdAndRole(id, Role.LIBRARIAN);
        if (account == null) throw new RuntimeException("This librarian ID cannot be found.");

        if (librarianUpdate.getUsername() != null) {
            validateAndSetUsername(id, account, librarianUpdate.getUsername());
        }

        if (librarianUpdate.getEmail() != null) {
            validateAndSetEmail(id, account, librarianUpdate.getEmail());
        }

        if (librarianUpdate.getLibraryID() != null) {
            Library library = libraryRepository
                    .findById(librarianUpdate.getLibraryID())
                    .orElseThrow(() -> new RuntimeException("This library ID cannot be found."));
            account.getLibrary().removeAccount(account);
            library.addAccount(account);
        }

        Account updatedAccount = accountRepository.save(account);

        return AccountMapper.updateLibToResponse(updatedAccount);
    }

    private void validateAndSetUsername(Long id, Account account, String username) {
        if (accountRepository.existsByUsername(username)) {
            Account existingMember = accountRepository.findByUsername(username);
            if (existingMember != null && !existingMember.getId().equals(id)) {
                throw new RuntimeException("This username has already been taken.");
            }
        }

        account.setUsername(username);
    }

    private void validateAndSetEmail(Long id, Account account, String email) {
        if (accountRepository.existsByEmail(email)) {
            Account existingMember = accountRepository.findByEmail(email);
            if (existingMember != null && !existingMember.getId().equals(id)) {
                throw new RuntimeException("This email has already been taken.");
            }
        }

        account.setEmail(email);
    }


    //           ------------------ updatePassword ------------------

    // Role: Member, Admin
    public String updateMemPassword(Long id, PasswordUpdate passwordUpdate) {
        Account account = accountRepository
                .findByIdAndRole(id, Role.MEMBER);
        if (account == null) throw new RuntimeException("This member ID cannot be found.");

        validatePasswordUpdateAndReset(account, passwordUpdate);

        return "The password has been reset successfully.";
    }

    // Role: Librarian, Admin
    public String updateLibPassword(Long id, PasswordUpdate passwordUpdate) {
        Account account = accountRepository
                .findByIdAndRole(id, Role.LIBRARIAN);
        if (account == null) throw new RuntimeException("This librarian ID cannot be found.");

        validatePasswordUpdateAndReset(account, passwordUpdate);

        return "The password has been reset successfully.";
    }

    private void validatePasswordUpdateAndReset(Account account, PasswordUpdate passwordUpdate) {

        // incorrect current password
        if (!encoder.matches(
                passwordUpdate.getCurrentPassword(),
                account.getPassword())) {

            throw new RuntimeException(
                    "The current password is incorrect.");
        }

        // passwords don't match
        if (!passwordUpdate.getNewPassword()
                .equals(passwordUpdate.getConfirmPassword())) {

            throw new RuntimeException(
                    "The passwords do not match.");
        }

        // reset
        account.setPassword(encoder.encode(passwordUpdate.getNewPassword()));
        accountRepository.save(account);
    }


    //           ------------------ deleteAccount ------------------

    // Role: Admin
    public void deleteAccount(Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("This ID cannot be found."));

        boolean hasActiveLendings = account.getLendings() != null &&
                account.getLendings().stream()
                        .anyMatch(l -> l.getStatus() == LendingStatus.ACTIVE);

        if (hasActiveLendings) {
            throw new RuntimeException("This account cannot be deleted as it has active lendings.");
        }

        accountRepository.delete(account);
    }


}
