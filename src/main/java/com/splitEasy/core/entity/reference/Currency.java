package com.splitEasy.core.entity.reference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "currencies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Currency {

    @Id
    @Column(length = 3)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 5)
    private String symbol;

    @Column(nullable = false)
    private int decimalPlaces;
}