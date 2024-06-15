package com.adjudicat.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "adj_departament")
@AllArgsConstructor
@NoArgsConstructor
public class DepartamentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_departament", nullable = false, unique = true)
    private Long idDepartament;

    @Column(name = "codi", unique = true)
    private Long codi;

    @Column(name = "nom", length = 128)
    private String nom;

}
