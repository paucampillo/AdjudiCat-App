package com.adjudicat.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "adj_ambit")
public class AmbitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ambit", nullable = false, unique = true)
    private Long idAmbit;

    @Column(name = "codi", unique = true)
    private Long codi;

    @Column(name = "nom", length = 128)
    private String nom;

}