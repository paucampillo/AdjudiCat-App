package com.adjudicat.controller.api;

import com.adjudicat.controller.dto.ValoracioCustomDTO;
import com.adjudicat.controller.dto.ValoracioDTO;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

@Api(tags = {"Valoració API"})
public interface ValoracioAPI {

    ResponseEntity<Object> saveValoracio(ValoracioCustomDTO valoracioDTO);

    ResponseEntity<Object> deleteValoracio(Long idValoracio);

    ResponseEntity<Page<ValoracioDTO>> listValoracionsReceptor(Integer page, Integer rpp, Long idUsuari);

    ResponseEntity<Page<ValoracioDTO>> listValoracionsEmisor(Integer page, Integer rpp, Long idUsuari);
}
