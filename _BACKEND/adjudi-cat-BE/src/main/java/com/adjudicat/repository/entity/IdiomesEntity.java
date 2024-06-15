package com.adjudicat.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "adj_idioma")
public class IdiomesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_idioma", nullable = false, unique = true)
    private Long idIdioma;

    @Column(name = "codi", nullable = false, unique = true, length = 8)
    private String codi;

    @Column(name = "nom", nullable = false, length = 16)
    private String nom;

}