package com.adjudicat.helper;

import com.adjudicat.controller.dto.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DTOHelper {

    public static AmbitDTO getAmbitDTO() {
        return AmbitDTO.builder()
                .idAmbit(1L)
                .codi(1L)
                .nom("Ambit 1")
                .build();
    }

    public static LotDTO getLotDTO() {
        return LotDTO.builder()
                .idLot(1L)
                .numero("1")
                .descripcio("Lot 1")
                .build();
    }

    public static IdiomesDTO getIdiomaCatDTO() {
        return IdiomesDTO.builder()
                .idIdioma(1L)
                .codi("CAT")
                .nom("Català")
                .build();
    }

    public static RolsDTO getRolPubDTO() {
        return RolsDTO.builder()
                .idRol(1L)
                .codi("PUB")
                .nom("Public")
                .visible(true)
                .build();
    }

    public static RolsDTO getRolUsuariDTO() {
        return RolsDTO.builder()
                .idRol(2L)
                .codi("USR")
                .nom("Usuari")
                .visible(true)
                .build();
    }

    public static UsuariDTO getUsuariPublicDTO() {
        return UsuariDTO.builder()
                .idUsuari(3L)
                .nom("public")
                .email("public@localhost")
                .contrasenya("public")
                .idioma(getIdiomaCatDTO())
                .rol(getRolPubDTO())
                .build();
    }

    public static UsuariDTO getUsuariDTO() {
        return UsuariDTO.builder()
                .idUsuari(2L)
                .nom("usuari")
                .email("usuari@localhost")
                .contrasenya("usuari")
                .idioma(getIdiomaCatDTO())
                .rol(getRolUsuariDTO())
                .build();
    }

    public static UsuariDTO getNewUsuariDTO() {
        return UsuariDTO.builder()
                .nom("usuari2")
                .email("usuari2@localhost")
                .contrasenya("usuari2")
                .idioma(getIdiomaCatDTO())
                .rol(getRolUsuariDTO())
                .build();
    }

    public static ContracteDTO getContracteDTO() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse("2020-01-01");
        return ContracteDTO.builder()
                .idContracte(1L)
                .codiExpedient("1")
                .ambit(getAmbitDTO())
                .lot(getLotDTO())
                .usuariCreacio(getUsuariPublicDTO())
                .terminiPresentacioOfertes(date)
                .build();
    }

    public static ChatbotAddDTO getChatbotAddDTO() {
        return ChatbotAddDTO.builder()
                .frase("frase")
                .keywords(List.of(new String[]{"keyword1", "keyword2"}))
                .build();
    }

    public static ValoracioCustomDTO getValoracioCustomDTO() {
        return ValoracioCustomDTO.builder()
                .idAutor(1L)
                .idReceptor(2L)
                .puntuacio(5)
                .descripcio("Descripcio")
                .build();
    }
}
