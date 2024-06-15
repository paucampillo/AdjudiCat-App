package com.adjudicat.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuariDTO {
    private Long idUsuari;
    private String nom;
    private String email;
    private String contrasenya;
    private String pais;
    private Integer codiPostal;
    private String direccio;
    private String telefon;
    private Boolean notificacionsActives;
    private String descripcio;
    private String enllacPerfilSocial;
    private String identNif;
    private Boolean bloquejat;
    private IdiomesDTO idioma;
    private RolsDTO rol;
    private OrganDTO organ;
    private Double valoracio;
    private Long numValoracions;

}