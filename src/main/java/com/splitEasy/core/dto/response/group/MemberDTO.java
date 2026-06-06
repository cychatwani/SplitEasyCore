package com.splitEasy.core.dto.response.group;

import com.splitEasy.core.entity.User;
import com.splitEasy.core.entity.group.GroupMembership;
import com.splitEasy.core.enums.GroupRole;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class MemberDTO {
    private String publicId;
    private String displayName;
    private String profilePicture;
    private GroupRole role;
    private Instant joinedAt;

    public static MemberDTO from(GroupMembership m) {
        User u = m.getUser();
        return MemberDTO.builder()
                .publicId(u.getPublicId())
                .displayName(u.getFullName())
                .profilePicture(u.getProfilePicture())
                .role(m.getRole())
                .joinedAt(m.getJoinedAt())
                .build();
    }
}