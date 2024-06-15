package com.adjudicat.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "adj_favorits")
@AllArgsConstructor
@NoArgsConstructor
public class FavoritsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_favorit", nullable = false, unique = true)
    private Long idFavorit;

    @ManyToOne
    @JoinColumn(name = "id_usuari", nullable = false)
    private UsuariEntity usuari;

    @ManyToOne
    @JoinColumn(name = "id_contracte", nullable = false)
    private ContracteEntity contracte;

}