package com.adjudicat.domain.service;

import com.adjudicat.controller.dto.ValoracioCustomDTO;
import com.adjudicat.controller.dto.ValoracioDTO;
import org.springframework.data.domain.Page;

public interface ValoracioService {

    void saveValoracio(ValoracioCustomDTO valoracioDTO);

    void deleteValoracio(Long idValoracio);

    Page<ValoracioDTO> findPaginatedReceptor(Integer rpp, Integer page, Long idUsuari);

    Page<ValoracioDTO> findPaginatedEmisor(Integer rpp, Integer page, Long idUsuari);
}
