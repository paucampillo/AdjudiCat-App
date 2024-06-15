package com.adjudicat.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "adj_oferta")
@AllArgsConstructor
@NoArgsConstructor
public class OfertaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_oferta", nullable = false, unique = true)
    private Long idOferta;

    @ManyToOne
    @JoinColumn(name = "id_empresa", nullable = false)
    private UsuariEntity empresa;

    @ManyToOne
    @JoinColumn(name = "id_contracte", nullable = false)
    private ContracteEntity contracte;

    @Column(name = "import_adjudicacio_sense")
    private Double importAdjudicacioSenseIva;

    @Column(name = "import_adjudicacio_amb_iva")
    private Double importAdjudicacioAmbIva;

    @Column(name = "data_hora_oferta")
    private LocalDateTime dataHoraOferta;

    @Column(name = "ganadora", nullable = false)
    private Boolean ganadora;

}
