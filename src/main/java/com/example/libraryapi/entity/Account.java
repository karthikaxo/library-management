package com.example.libraryapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Entity
@Table
@Data // getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor
@AllArgsConstructor
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) // store as string
    @Column(nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "library_id") // nullable true as 'MEMBERS' don't require library
    private Library library; // only for librarian role

    @Column(nullable = false)
    private LocalDate createDate;

    @Column
    private LocalDate lastLogin;

    @OneToMany(mappedBy = "account")
    private List<Lending> lendings; // books borrowed, returned etc


    // methods from UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + role.name())
        );
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // is account locked
        return true;
    }

    @Override
    public boolean isEnabled() { // is account active
        return true;
    }


}
