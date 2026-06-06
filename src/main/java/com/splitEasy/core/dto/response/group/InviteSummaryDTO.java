package com.splitEasy.core.dto.response.group;

import com.splitEasy.core.entity.group.GroupInviteLink;
import com.splitEasy.core.enums.InviteLinkType;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class InviteSummaryDTO {
    private String id;
    private InviteLinkType type;
    private Instant expiresAt;
    private Integer maxUses;
    private Integer useCount;
    private Instant createdAt;

    public static InviteSummaryDTO from(GroupInviteLink l) {
        return InviteSummaryDTO.builder()
                .id(l.getId())
                .type(l.getType())
                .expiresAt(l.getExpiresAt())
                .maxUses(l.getMaxUses())
                .useCount(l.getUseCount())
                .createdAt(l.getCreatedAt())
                .build();
    }
}