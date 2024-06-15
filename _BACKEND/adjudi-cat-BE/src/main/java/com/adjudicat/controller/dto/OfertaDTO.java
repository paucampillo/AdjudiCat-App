package com.adjudicat.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfertaDTO {
    private Long idOferta;
    private UsuariDTO empresa;
    private ContracteDTO contracte;
    private Double importAdjudicacioSenseIva;
    private Double importAdjudicacioAmbIva;
    private LocalDateTime dataHoraOferta;
    private Boolean ganadora;
}
