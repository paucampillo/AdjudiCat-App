package com.adjudicat.controller.impl;

import com.adjudicat.controller.api.OfertaAPI;
import com.adjudicat.controller.dto.ContracteDTO;
import com.adjudicat.controller.dto.LicitarDTO;
import com.adjudicat.controller.dto.OfertaDTO;
import com.adjudicat.domain.service.OfertaService;
import com.adjudicat.exception.RepositoryException;
import com.adjudicat.exception.constants.RepositoryConstants;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/adj/oferta")
public class OfertaController extends BaseController implements OfertaAPI {

    private final OfertaService ofertaService;

    @Override
    @PostMapping("/licitar")
    public ResponseEntity<Object> saveOferta(@RequestBody final LicitarDTO licitarDTO) throws RepositoryException {
        ofertaService.saveOferta(licitarDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @GetMapping("/historicOfertes/{idContracte}")
    public List<OfertaDTO> getHistoricOfertes(@PathVariable final Long idContracte) {
        return ofertaService.getHistoricOfertes(idContracte);
    }

    @Override
    @PostMapping("/historicOfertesByUsuari")
    public ResponseEntity<Page<ContracteDTO>> getHistoricOfertesByUsuari(
        @ApiParam(value = "Nombre de pàgina", example = "1") @RequestParam(defaultValue = "1", value = "page", required = false) Integer page,
        @ApiParam(value = "Valors per pàgina", example = "10") @RequestParam(defaultValue = "10", value = "rpp", required = false) Integer rpp,
        @ApiParam(value = "Id usuari", example = "0") @RequestParam(defaultValue = "0", value = "idUsuari", required = true) Long idUsuari) {

        Page<ContracteDTO> filtrePage = ofertaService.findHistoricOfertesByUsuari(page, rpp, idUsuari);
        return ResponseEntity.ok(filtrePage);
    }

    @Override
    @PostMapping("/ofertesByUsuari")
    public ResponseEntity<Page<ContracteDTO>> getOfertesByUsuari(
        @ApiParam(value = "Nombre de pàgina", example = "1") @RequestParam(defaultValue = "1", value = "page", required = false) Integer page,
        @ApiParam(value = "Valors per pàgina", example = "10") @RequestParam(defaultValue = "10", value = "rpp", required = false) Integer rpp,
        @ApiParam(value = "Id usuari", example = "0") @RequestParam(defaultValue = "0", value = "idUsuari", required = true) Long idUsuari) {

        Page<ContracteDTO> filtrePage = ofertaService.findOfertesByUsuari(page, rpp, idUsuari);
        return ResponseEntity.ok(filtrePage);
    }
}
