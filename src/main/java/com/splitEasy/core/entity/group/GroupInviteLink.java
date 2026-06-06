package com.splitEasy.core.entity.group;

import com.github.f4b6a3.ulid.Ulid;
import com.splitEasy.core.entity.User;
import com.splitEasy.core.enums.InviteLinkType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(
        name = "group_invite_links",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_group_invite_link_code",
                columnNames = {"code"}
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupInviteLink {

    @Id
    @Column(name = "id", updatable = false, nullable = false, length = 26)
    private String id;  // ULID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Column(nullable = false, updatable = false, length = 16)
    private String code;  // short, shareable, unique — service-generated

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private InviteLinkType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    private Instant expiresAt;   // nullable — null for PRIMARY

    private Integer maxUses;     // nullable — null means unlimited

    @Column(nullable = false)
    @Builder.Default
    private Integer useCount = 0;

    @Column(nullable = false)
    @Builder.Default
    private boolean isActive = true;   // maps to is_active

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();

    @PrePersist
    private void prePersist() {
        if (id == null) {
            id = Ulid.fast().toString();
        }
    }
}