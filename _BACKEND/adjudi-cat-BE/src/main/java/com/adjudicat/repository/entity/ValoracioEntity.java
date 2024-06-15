package com.adjudicat.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "adj_valoracio")
@AllArgsConstructor
@NoArgsConstructor
public class ValoracioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_valoracio", nullable = false, unique = true)
    private Long idValoracio;

    @ManyToOne
    @JoinColumn(name = "id_organisme", nullable = false)
    private UsuariEntity autor;

    @ManyToOne
    @JoinColumn(name = "id_empresa_valorada", nullable = false)
    private UsuariEntity receptor;

    @Column(name = "puntuacio", nullable = false)
    private Integer puntuacio;

    @Column(name = "descripcio")
    private String descripcio;

    @Column(name = "data_hora_valoracio", nullable = false)
    private LocalDateTime dataHoraValoracio;

}