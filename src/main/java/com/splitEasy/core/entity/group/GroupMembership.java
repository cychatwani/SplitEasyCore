package com.splitEasy.core.entity.group;

import com.github.f4b6a3.ulid.Ulid;
import com.splitEasy.core.entity.User;
import com.splitEasy.core.enums.GroupRole;
import com.splitEasy.core.enums.MembershipStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(
        name = "group_memberships",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_group_membership_group_user",
                columnNames = {"group_id", "user_id"}
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupMembership {

    @Id
    @Column(name = "id", updatable = false, nullable = false, length = 26)
    private String id;  // ULID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private GroupRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private MembershipStatus status = MembershipStatus.ACTIVE;

    @Column(nullable = false)
    @Builder.Default
    private boolean isDeleted = false;  // maps to is_deleted

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private Instant joinedAt = Instant.now();

    private Instant leftAt;  // nullable, set on leave/remove

    @PrePersist
    private void prePersist() {
        if (id == null) {
            id = Ulid.fast().toString();
        }
    }
}