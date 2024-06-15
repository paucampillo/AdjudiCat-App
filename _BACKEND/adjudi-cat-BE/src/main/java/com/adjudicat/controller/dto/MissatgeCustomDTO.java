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
public class MissatgeCustomDTO {
    private Long idMissatge;
    private Long idemissor;
    private Long idreceptor;
    private String missatge;
    private LocalDateTime dataHoraEnvio;
}

