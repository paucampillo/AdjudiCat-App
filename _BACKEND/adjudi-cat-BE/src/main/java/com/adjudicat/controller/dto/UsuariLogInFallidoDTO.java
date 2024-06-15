package com.adjudicat.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuariLogInFallidoDTO {
    private Long idUsuariLogInFallido;
    private Integer numeroIntentos;
    private LocalDateTime dataFinalizacionBan;
    private UsuariDTO usuari;
}