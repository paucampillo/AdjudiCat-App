package com.adjudicat.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "adj_contracte")
@AllArgsConstructor
@NoArgsConstructor
public class ContracteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contracte", nullable = false, unique = true)
    private Long idContracte;

    @Column(name = "codi_expedient", unique = true)
    private String codiExpedient;


    @Column(name = "tipus_contracte", length = 128)
    private String tipusContracte;

    @Column(name = "subtipus_contracte", length = 512)
    private String subtipusContracte;

    @Column(name = "procediment")
    private String procediment;

    @Column(name = "objecte_contracte", length = 512)
    private String objecteContracte;

    @Column(name = "pressupost_licitacio")
    private Double pressupostLicitacio;

    @Column(name = "valor_estimat_contracte")
    private Double valorEstimatContracte;

    @Column(name = "lloc_execucio")
    private String llocExecucio;

    @Column(name = "duracio_contracte")
    private String duracioContracte;

    @Column(name = "termini_presentacio_ofertes")
    private Date terminiPresentacioOfertes;

    @Column(name = "data_publicacio_anunci")
    private Date dataPublicacioAnunci;

    @Column(name = "ofertes_rebudes")
    private Integer ofertesRebudes;

    @Column(name = "resultat", length = 64)
    private String resultat;

    @Column(name = "enllac_publicacio")
    private String enllacPublicacio;

    @Column(name = "data_adjudicacio_contracte")
    private Date dataAdjudicacioContracte;

    @ManyToOne
    @JoinColumn(name = "id_ambit")
    private AmbitEntity ambit;

    @ManyToOne
    @JoinColumn(name = "id_lot")
    private LotEntity lot;

    @ManyToOne
    @JoinColumn(name = "id_usuari_creacio")
    private UsuariEntity usuariCreacio;

}
