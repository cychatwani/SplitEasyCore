package com.splitEasy.core.repository;

import com.splitEasy.core.entity.group.GroupInviteLink;
import com.splitEasy.core.enums.InviteLinkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GroupInviteLinkRepository extends JpaRepository<GroupInviteLink, String> {

    @Modifying(flushAutomatically = true)
    @Query("""
            UPDATE GroupInviteLink l SET l.isActive = false
            WHERE l.group.id = :groupId AND l.type = :type AND l.isActive = true
            """)
    int deactivateActiveLinksOfType(@Param("groupId") String groupId,
                                    @Param("type") InviteLinkType type);

    Optional<GroupInviteLink> findByCode(String code);

    @Modifying(flushAutomatically = true)
    @Query("""
        UPDATE GroupInviteLink l SET l.useCount = l.useCount + 1
        WHERE l.id = :id AND (l.maxUses IS NULL OR l.useCount < l.maxUses)
        """)
    int incrementUseCountIfAvailable(@Param("id") String id);

    List<GroupInviteLink> findByGroupIdAndIsActiveTrue(String groupId);
}