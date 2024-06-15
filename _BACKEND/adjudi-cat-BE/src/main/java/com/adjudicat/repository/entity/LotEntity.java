package com.adjudicat.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "adj_lot")
public class LotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lot", nullable = false, unique = true)
    private Long idLot;

    @Column(name = "numero", unique = true, length = 32)
    private String numero;

    @Column(name = "descripcio")
    private String descripcio;

}