package com.splitEasy.core.repository;

import com.splitEasy.core.entity.group.GroupInviteLink;
import com.splitEasy.core.enums.InviteLinkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupInviteLinkRepository extends JpaRepository<GroupInviteLink, String> {

    @Modifying(flushAutomatically = true)
    @Query("""
            UPDATE GroupInviteLink l SET l.isActive = false
            WHERE l.group.id = :groupId AND l.type = :type AND l.isActive = true
            """)
    int deactivateActiveLinksOfType(@Param("groupId") String groupId,
                                    @Param("type") InviteLinkType type);

    List<GroupInviteLink> findByGroupIdAndIsActiveTrue(String groupId);
}