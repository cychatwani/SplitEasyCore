package com.splitEasy.core.security;

import com.splitEasy.core.enums.InviteLinkType;
import com.splitEasy.core.enums.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.crypto.SecretKey;


@Component
public class JwtService {

    private final SecretKey key;
    private final long accessExpiration;
    private final long refreshExpiration;
    private final long emailVerificationExpiration;

    public JwtService(
            @Value("${spring.jwt.secret}") String secret,
            @Value("${spring.jwt.access-token-expiration}") long accessExpiration,
            @Value("${spring.jwt.refresh-token-expiration}") long refreshExpiration,
            @Value("${spring.jwt.email-verification-expiration}") long emailVerificationExpiration) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
        this.emailVerificationExpiration = emailVerificationExpiration;
    }

    // ---- Generation (existing API — unchanged) ----

    public String generateAccessToken(String publicId) {
        return buildToken(publicId, TokenType.ACCESS.getValue(), accessExpiration);
    }

    public String generateRefreshToken(String publicId) {
        return buildToken(publicId, TokenType.REFRESH.getValue(), refreshExpiration);
    }

    public String generateEmailVerificationToken(String publicId) {
        return buildToken(publicId, TokenType.EMAIL_VERIFICATION.getValue(), emailVerificationExpiration);
    }

    // ---- Generation: invite (typed in; the claim map is confined here) ----

    public String generateGroupInviteToken(String code, List<String> invitedUsers, long ttl) {
        Map<String, Object> extra = (invitedUsers == null || invitedUsers.isEmpty())
                ? null
                : Map.of("invitedUsers", invitedUsers);
        return buildToken(code, TokenType.GROUP_INVITE.getValue(), extra, ttl);
    }

    // ---- Generation primitives (private) ----

    private String buildToken(String subject, String type, Map<String, Object> extraClaims, long ttl) {
        var builder = Jwts.builder()
                .subject(subject)
                .claim("type", type)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ttl));
        if (extraClaims != null) {
            extraClaims.forEach(builder::claim);
        }
        return builder.signWith(key).compact();
    }

    private String buildToken(String subject, String type, long ttl) {
        return buildToken(subject, type, null, ttl);
    }

    // ---- Parsing ----

    public TokenClaims parse(String token) {
        Claims c = verifyAndParse(token);
        try {
            TokenType type = TokenType.fromValue(c.get("type", String.class));
            return switch (type) {
                case ACCESS -> new TokenClaims.Access(c.getSubject());
                case REFRESH -> new TokenClaims.Refresh(c.getSubject());
                case EMAIL_VERIFICATION -> new TokenClaims.EmailVerification(c.getSubject());
                case GROUP_INVITE -> {
                    @SuppressWarnings("unchecked")
                    List<String> invited = (List<String>) c.get("invitedUsers", List.class);
                    yield new TokenClaims.GroupInvite(c.getSubject(), invited);
                }
            };
        } catch (IllegalArgumentException | NullPointerException e) {
            // unknown "type", or a missing/garbage "inviteType" on a group-invite token
            throw new BadCredentialsException("Malformed token claims", e);
        }
    }

    /** Typed-expect helper for the invite join path. */
    public TokenClaims.GroupInvite parseGroupInvite(String token) {
        if (parse(token) instanceof TokenClaims.GroupInvite invite) {
            return invite;
        }
        throw new BadCredentialsException("Invalid token type");
    }

    private Claims verifyAndParse(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new BadCredentialsException("Token has expired", e);
        } catch (SecurityException | IllegalArgumentException e) {
            throw new BadCredentialsException("Invalid JWT token", e);
        }
    }

    // ---- Convenience (existing API — now over parse) ----

    public String getUserPublicIdFromToken(String token, String allowedAccessType) {
        TokenClaims claims = parse(token);
        if (!claims.type().getValue().equals(allowedAccessType)) {
            throw new BadCredentialsException("Invalid token type");
        }
        return claims.subject();
    }

    public String getSubject(String token) {
        return parse(token).subject();
    }

    public String getType(String token) {
        return parse(token).type().getValue();
    }
}