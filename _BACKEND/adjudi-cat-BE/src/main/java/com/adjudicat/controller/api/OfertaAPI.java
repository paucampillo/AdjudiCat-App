package com.adjudicat.controller.api;

import com.adjudicat.controller.dto.ContracteDTO;
import com.adjudicat.controller.dto.LicitarDTO;
import com.adjudicat.controller.dto.OfertaDTO;
import com.adjudicat.exception.RepositoryException;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = {"Oferta API"})
public interface OfertaAPI {
    ResponseEntity<Object> saveOferta(LicitarDTO licitarDTO) throws RepositoryException;

    List<OfertaDTO> getHistoricOfertes(Long idContracte);

    ResponseEntity<Page<ContracteDTO>> getHistoricOfertesByUsuari(Integer page, Integer rpp, Long idUsuari);

    ResponseEntity<Page<ContracteDTO>> getOfertesByUsuari(Integer page, Integer rpp, Long idUsuari);
}
