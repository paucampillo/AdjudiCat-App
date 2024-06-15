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
public class ValoracioCustomDTO {
    private Long idAutor;
    private Long idReceptor;
    private Integer puntuacio;
    private String descripcio;
}