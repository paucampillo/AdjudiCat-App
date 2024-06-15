package com.adjudicat.controller.impl;

import com.adjudicat.controller.api.ValoracioAPI;
import com.adjudicat.controller.dto.ValoracioCustomDTO;
import com.adjudicat.controller.dto.ValoracioDTO;
import com.adjudicat.domain.service.ValoracioService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/adj/valoracio")
public class ValoracioController extends BaseController implements ValoracioAPI {

    private final ValoracioService valoracioService;

    @Override
    @PostMapping(value = "/valorar")
    public ResponseEntity<Object> saveValoracio(@RequestBody final ValoracioCustomDTO valoracioDTO) {
        try {
            valoracioService.saveValoracio(valoracioDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @DeleteMapping(value = "/eliminar/{idValoracio}")
    public ResponseEntity<Object> deleteValoracio(@PathVariable("idValoracio") final Long idValoracio) {
        try {
            valoracioService.deleteValoracio(idValoracio);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @GetMapping(value = "/listarPorReceptor")
    public ResponseEntity<Page<ValoracioDTO>> listValoracionsReceptor(
        @ApiParam(value = "Nombre de pàgina", example = "1") @RequestParam(defaultValue = "1", value = "page", required = false) Integer page,
        @ApiParam(value = "Valors per pàgina", example = "10") @RequestParam(defaultValue = "10", value = "rpp", required = false) Integer rpp,
        @ApiParam(value = "Id usuari") @RequestParam(value = "idUsuari") Long idUsuari) {

        Page<ValoracioDTO> filtrePage = valoracioService.findPaginatedReceptor(rpp, page, idUsuari);
        return ResponseEntity.ok(filtrePage);
    }

    @Override
    @GetMapping(value = "/listarPorEmisor")
    public ResponseEntity<Page<ValoracioDTO>> listValoracionsEmisor(
            @ApiParam(value = "Nombre de pàgina", example = "1") @RequestParam(defaultValue = "1", value = "page", required = false) Integer page,
            @ApiParam(value = "Valors per pàgina", example = "10") @RequestParam(defaultValue = "10", value = "rpp", required = false) Integer rpp,
            @ApiParam(value = "Id usuari") @RequestParam(value = "idUsuari") Long idUsuari) {

        Page<ValoracioDTO> filtrePage = valoracioService.findPaginatedEmisor(rpp, page, idUsuari);
        return ResponseEntity.ok(filtrePage);
    }
}
