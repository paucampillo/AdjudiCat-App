package com.adjudicat.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FiltreDTO {
    private String codiExpedient;
    private String tipusContracte;
    private String ambit;
    private String procediment;
    private String llocExecucio;
    private Double valorContracte;
    private String objecteContracte;
}