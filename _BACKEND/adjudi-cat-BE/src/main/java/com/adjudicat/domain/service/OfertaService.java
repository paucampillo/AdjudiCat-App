package com.adjudicat.domain.service;

import com.adjudicat.controller.dto.ContracteDTO;
import com.adjudicat.controller.dto.LicitarDTO;
import com.adjudicat.controller.dto.OfertaDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OfertaService {

    void saveOferta(LicitarDTO licitarDTO);
    List<OfertaDTO> getHistoricOfertes(Long idContracte);
    Page<ContracteDTO> findHistoricOfertesByUsuari(Integer page, Integer rpp, Long idUsuari);
    Page<ContracteDTO> findOfertesByUsuari(Integer page, Integer rpp, Long idUsuari);
    List<Long> getLicitadorsContracte(Long idContracte);
    void deleteOfertesByContracte(Long idContracte);
}
