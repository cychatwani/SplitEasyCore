package com.splitEasy.core.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notification_templates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String type; // e.g. "EMAIL_VERIFICATION", "PASSWORD_RESET"

    @Column(nullable = false)
    private String subject; // e.g. "Verify your email"

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body; // e.g. "Hi {{firstName}}, click here: {{link}}"

    @Column(nullable = false, length = 20)
    private String channel; // e.g. "EMAIL", "SMS", "PUSH"
}