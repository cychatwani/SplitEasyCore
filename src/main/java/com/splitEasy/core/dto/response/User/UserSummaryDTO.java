package com.splitEasy.core.dto.response.User;

import com.splitEasy.core.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSummaryDTO {
    private String publicId;
    private String displayName;

    public static UserSummaryDTO from(User user) {
        return UserSummaryDTO.builder()
                .publicId(user.getPublicId())
                .displayName(user.getFullName())
                .build();
    }
}