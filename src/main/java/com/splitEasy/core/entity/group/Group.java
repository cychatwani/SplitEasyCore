package com.splitEasy.core.entity.group;

import com.github.f4b6a3.ulid.Ulid;
import com.splitEasy.core.entity.User;
import com.splitEasy.core.entity.reference.Currency;
import com.splitEasy.core.enums.GroupType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {

    @Id
    @Column(name = "id", updatable = false, nullable = false, length = 26)
    private String id;  // ULID, exposed directly in API

    @Column(nullable = false)
    private String name;

    private String description;  // nullable

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "default_currency", nullable = false)
    private Currency defaultCurrency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private GroupType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(nullable = false)
    @Builder.Default
    private Integer memberCount = 0;

    @Column(nullable = false)
    @Builder.Default
    private boolean isDeleted = false;  // maps to is_deleted

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    @Builder.Default
    private List<GroupPreference> preferences = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();

    @Column(nullable = false)
    @Builder.Default
    private Instant updatedAt = Instant.now();

    @PrePersist
    private void prePersist() {
        if (id == null) {
            id = Ulid.fast().toString();  // same pattern as User.publicId
        }
    }

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = Instant.now();
    }
}