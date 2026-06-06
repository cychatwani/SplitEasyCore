package com.splitEasy.core.dto.requests.group;

import com.splitEasy.core.enums.InviteLinkType;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateInviteRequestDTO {

    @NotNull
    private InviteLinkType type;

    private List<String> invitedUsers;   // optional — null/empty = open to anyone

    private Instant expiresAt;            // TEMPORARY only
    private Integer maxUses;              // TEMPORARY only

    @AssertTrue(message = "PRIMARY invites cannot set expiresAt or maxUses")
    public boolean isLimitsValidForType() {
        if (type != InviteLinkType.PRIMARY) {
            return true;            // rule only applies to PRIMARY
        }
        return expiresAt == null && maxUses == null;
    }
}