package com.splitEasy.core.dto.response.group;

import com.splitEasy.core.enums.InviteLinkType;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class InviteResponseDTO {
    private String token;
    private InviteLinkType type;
    private Instant expiresAt;
    private Integer maxUses;
    private Integer useCount;
    private Instant createdAt;
}