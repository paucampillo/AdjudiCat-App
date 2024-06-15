package com.adjudicat.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoritsDTO {

    private Long idFavorit;
    private UsuariDTO usuari;
    private ContracteDTO contracte;

}