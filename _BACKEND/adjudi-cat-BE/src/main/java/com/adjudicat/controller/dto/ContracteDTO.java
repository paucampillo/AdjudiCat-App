package com.adjudicat.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContracteDTO {
    private Long idContracte;
    private String codiExpedient;
    private String tipusContracte;
    private String subtipusContracte;
    private String procediment;
    private String objecteContracte;
    private Double pressupostLicitacio;
    private Double valorEstimatContracte;
    private String llocExecucio;
    private String duracioContracte;
    private Date terminiPresentacioOfertes;
    private Date dataPublicacioAnunci;
    private Integer ofertesRebudes;
    private String resultat;
    private String enllacPublicacio;
    private Date dataAdjudicacioContracte;
    private AmbitDTO ambit;
    private LotDTO lot;
    private UsuariDTO usuariCreacio;
    private Boolean preferit;
}
