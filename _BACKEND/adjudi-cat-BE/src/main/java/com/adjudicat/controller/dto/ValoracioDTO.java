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
public class ValoracioDTO {
    private Long idValoracio;
    private UsuariDTO autor;
    private UsuariDTO receptor;
    private Integer puntuacio;
    private String descripcio;
    private LocalDateTime dataHoraValoracio;
}

