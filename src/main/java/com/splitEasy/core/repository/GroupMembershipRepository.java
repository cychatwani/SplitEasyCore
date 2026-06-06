package com.splitEasy.core.repository;

import com.splitEasy.core.entity.group.Group;
import com.splitEasy.core.entity.group.GroupMembership;
import com.splitEasy.core.enums.GroupType;
import com.splitEasy.core.enums.MembershipStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface GroupMembershipRepository extends JpaRepository<GroupMembership, String> {

    Optional<GroupMembership> findByGroupIdAndUserIdAndStatus(
            String groupId, Long userId, MembershipStatus status);

    @Query("""
            SELECT m.user.publicId FROM GroupMembership m
            WHERE m.group.id = :groupId
              AND m.user.publicId IN :publicIds
              AND m.status = :status
            """)
    List<String> findExistingMemberPublicIds(@Param("groupId") String groupId,
                                             @Param("publicIds") Collection<String> publicIds,
                                             @Param("status") MembershipStatus status);

    /** Groups the user belongs to in the given status, newest first, optional type filter. */
    @Query(value = """
            SELECT m.group FROM GroupMembership m
            WHERE m.user.id = :userId
              AND m.status = :status
              AND m.group.isDeleted = false
              AND (:type IS NULL OR m.group.type = :type)
            ORDER BY m.group.createdAt DESC
            """,
            countQuery = """
            SELECT COUNT(m) FROM GroupMembership m
            WHERE m.user.id = :userId
              AND m.status = :status
              AND m.group.isDeleted = false
              AND (:type IS NULL OR m.group.type = :type)
            """)
    Page<Group> findGroupsByUserIdAndStatus(@Param("userId") Long userId,
                                            @Param("status") MembershipStatus status,
                                            @Param("type") GroupType type,
                                            Pageable pageable);

    /** Members of a group in the given status, user eagerly fetched (avoids N+1 on member details). */
    @Query("""
            SELECT m FROM GroupMembership m
            JOIN FETCH m.user
            WHERE m.group.id = :groupId AND m.status = :status
            """)
    List<GroupMembership> findMembersByGroupIdAndStatus(@Param("groupId") String groupId,
                                                        @Param("status") MembershipStatus status);
}