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
public class MissatgeDTO {
    private Long idMissatge;
    private UsuariDTO emissor;
    private UsuariDTO receptor;
    private String missatge;
    private LocalDateTime dataHoraEnvio;
}

