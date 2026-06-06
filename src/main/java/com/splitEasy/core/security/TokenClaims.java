package com.splitEasy.core.security;

import com.splitEasy.core.enums.InviteLinkType;
import com.splitEasy.core.enums.TokenType;

import java.util.List;

/**
 * Typed, exhaustive taxonomy of every JWT this app issues. One record per token
 * type — each type's claim shape is visible here, and adding a new TokenType
 * forces the parse switch in JwtService to handle it.
 */
public sealed interface TokenClaims {

    String subject();
    TokenType type();

    record Access(String subject) implements TokenClaims {
        @Override public TokenType type() { return TokenType.ACCESS; }
    }

    record Refresh(String subject) implements TokenClaims {
        @Override public TokenType type() { return TokenType.REFRESH; }
    }

    record EmailVerification(String subject) implements TokenClaims {
        @Override public TokenType type() { return TokenType.EMAIL_VERIFICATION; }
    }

    record GroupInvite(String subject, List<String> invitedUsers) implements TokenClaims {
        public GroupInvite {
            invitedUsers = (invitedUsers == null) ? List.of() : List.copyOf(invitedUsers);
        }

        @Override public TokenType type() { return TokenType.GROUP_INVITE; }

        public String code() { return subject; }   // subject carries the link code

        public boolean isOpen() { return invitedUsers.isEmpty(); }

        /** Empty/absent list = anyone; otherwise the joiner's publicId must be present. */
        public boolean allows(String userPublicId) {
            return isOpen() || invitedUsers.contains(userPublicId);
        }
    }
}