package com.adjudicat.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReturnLoginDTO {
    private String token;
    private Long idUsuari;
    private String codiRol;
    private boolean loginCorrect;
    private String errorMsg;
}
