package com.example.devicemonitoringserver.domain.product.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Product {
    @Id @GeneratedValue
    private Long id;

    private String name;
}
