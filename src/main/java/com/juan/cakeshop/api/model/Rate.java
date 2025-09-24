package com.juan.cakeshop.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rateId;
    private Integer score;
    private String comment;
    private LocalDate date;
    @ManyToOne()
    @JoinColumn(name = "nip", nullable = false)
    private User user;
    @ManyToOne()
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
