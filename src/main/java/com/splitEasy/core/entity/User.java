package com.splitEasy.core.entity;

import com.splitEasy.core.enums.AuthProvider;
import jakarta.persistence.*;
import lombok.*;
import com.github.f4b6a3.ulid.Ulid;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment BIGINT
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;  // internal numeric PK (never exposed)

    @Column(nullable = false, unique = true, updatable = false, length = 26)
    private String publicId;  // ULID exposed in API

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String fullName;

    private String profilePicture;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AuthProvider authProvider;

    @Column(length = 100)
    private String passwordHash;

    @Column(nullable = false)
    @Builder.Default
    private boolean emailVerified = false;


    @PrePersist
    private void prePersist() {
        if (publicId == null) {
            publicId = Ulid.fast().toString(); // generate ULID for API
        }
        updateFullName();
    }

    @PreUpdate
    private void updateFullName() {
        this.fullName = (firstName != null ? firstName : "") +
                (lastName != null ? " " + lastName : "");
    }
}


