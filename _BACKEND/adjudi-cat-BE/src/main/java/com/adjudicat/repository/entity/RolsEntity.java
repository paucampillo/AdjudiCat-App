package com.adjudicat.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "adj_rol")
public class RolsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol", nullable = false, unique = true)
    private Long idRol;

    @Column(name = "codi", nullable = false, unique = true, length = 8)
    private String codi;

    @Column(name = "nom", nullable = false, length = 16)
    private String nom;

    @Column(name = "visible", nullable = false)
    private Boolean visible;

}