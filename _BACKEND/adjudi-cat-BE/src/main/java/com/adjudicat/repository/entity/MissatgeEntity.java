package com.adjudicat.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "adj_missatge")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MissatgeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_missatge", nullable = false, unique = true)
    private Long idMissatge;

    @ManyToOne
    @JoinColumn(name = "id_emissor", nullable = false)
    private UsuariEntity emissor;

    @ManyToOne
    @JoinColumn(name = "id_receptor", nullable = false)
    private UsuariEntity receptor;

    @Column(name = "missatge", nullable = false)
    private String missatge;

    @Column(name = "data_hora_envio", nullable = false)
    private LocalDateTime dataHoraEnvio;

}
