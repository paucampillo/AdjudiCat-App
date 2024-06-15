package com.adjudicat.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "adj_organ")
public class OrganEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_organ", nullable = false, unique = true)
    private Long idOrgan;

    @Column(name = "codi", unique = true)
    private Long codi;

    @Column(name = "nom", length = 128)
    private String nom;

    @ManyToOne
    @JoinColumn(name = "id_departament")
    private DepartamentEntity departament;

}