package com.splitEasy.core.dto.response.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String profilePicture;
    private String email;
    private boolean hasProfile;
}
