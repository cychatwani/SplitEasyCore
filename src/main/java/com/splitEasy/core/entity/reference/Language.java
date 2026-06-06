package com.splitEasy.core.entity.reference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "languages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Language {

    @Id
    @Column(length = 5)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nativeName;
}