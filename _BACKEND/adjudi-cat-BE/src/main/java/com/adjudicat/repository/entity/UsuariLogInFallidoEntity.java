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
@Table(name = "adj_usuari_login_fallido")
public class UsuariLogInFallidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_login_fallido", nullable = false, unique = true)
    private Long idUsuariLogInFallido;

    @Column(name = "numero_intentos", nullable = false)
    private Integer numeroIntentos;

    @Column(name = "data_finalizacion_ban", nullable = false)
    private LocalDateTime dataFinalizacionBan;

    @ManyToOne
    @JoinColumn(name = "id_usuari", nullable = false)
    private UsuariEntity usuari;

}