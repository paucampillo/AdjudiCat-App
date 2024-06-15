package com.adjudicat.controller.impl;

import com.adjudicat.controller.api.ContracteAPI;
import com.adjudicat.controller.dto.ContracteDTO;
import com.adjudicat.controller.dto.FiltreDTO;
import com.adjudicat.domain.model.mapper.ContracteMapper;
import com.adjudicat.domain.service.ContracteService;
import com.adjudicat.exception.RepositoryException;
import com.adjudicat.exception.constants.RepositoryConstants;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/adj/contracte")
public class ContracteController extends BaseController implements ContracteAPI {

    private final ContracteService contracteService;
    private final ContracteMapper contracteMapper;

    @Override
    @PostMapping(value = "/paginacio")
    public ResponseEntity<Page<ContracteDTO>> findPaginated(
            @ApiParam(value = "Nombre de pàgina", example = "1") @RequestParam(defaultValue = "1", value = "page", required = false) Integer page,
            @ApiParam(value = "Valors per pàgina", example = "10") @RequestParam(defaultValue = "10", value = "rpp", required = false) Integer rpp,
            @ApiParam(value = "Camp ordenació", example = "codiExpedient") @RequestParam(defaultValue = "codiExpedient", value = "sort", required = false) String sort,
            @ApiParam(value = "Id usuari") @RequestParam(value = "idUsuari") Long idUsuari,
            @ApiParam(value = "JSON per la consulta", required = true) @RequestBody FiltreDTO filtreDTO) {

        Page<ContracteDTO> filtrePage = contracteService.findPaginated(filtreDTO, rpp, page, sort, idUsuari);
        return ResponseEntity.ok(filtrePage);
    }

    @Override
    @PostMapping(value = "/historic")
    public ResponseEntity<Page<ContracteDTO>> findPaginatedHistoric(
            @ApiParam(value = "Nombre de pàgina", example = "1") @RequestParam(defaultValue = "1", value = "page", required = false) Integer page,
            @ApiParam(value = "Valors per pàgina", example = "10") @RequestParam(defaultValue = "10", value = "rpp", required = false) Integer rpp,
            @ApiParam(value = "Camp ordenació", example = "codiExpedient") @RequestParam(defaultValue = "codiExpedient", value = "sort", required = false) String sort,
            @ApiParam(value = "Id usuari") @RequestParam(value = "idUsuari") Long idUsuari,
            @ApiParam(value = "JSON per la consulta", required = true) @RequestBody FiltreDTO filtreDTO) {

        Page<ContracteDTO> filtrePage = contracteService.findPaginatedHistoric(filtreDTO, rpp, page, sort, idUsuari);
        return ResponseEntity.ok(filtrePage);
    }

    @Override
    @PostMapping(value = "/publicar")
    public ResponseEntity<Object> saveContracte(@RequestBody final ContracteDTO contracteDTO) {
        try {
            contracteService.saveContracte(contracteDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (DataAccessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @DeleteMapping(value = "/{idContracte}")
    public ResponseEntity<Object> deleteContracte(@PathVariable("idContracte") final Long idContracte) {
        contracteService.deleteContracte(idContracte);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/findContracteCodi")
    public ResponseEntity<ContracteDTO> findByCodiExpedient(@RequestParam("codiExpedient") final String codiExpedient) {
        ContracteDTO contracteDTO = contracteMapper.toDTO(contracteService.findByCodi(codiExpedient));
        if (contracteDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(contracteDTO);
    }

    @Override
    @GetMapping(value = "/{idContracte}/{idUsuari}")
    public ResponseEntity<ContracteDTO> findByIdContracte(@PathVariable final Long idContracte, @PathVariable final Long idUsuari) {
            ContracteDTO contracteDTO = contracteService.get(idContracte, idUsuari);
            return ResponseEntity.ok(contracteDTO);
    }

    @Override
    @PostMapping(value = "/historicByCreador")
    public ResponseEntity<Page<ContracteDTO>> findPaginatedHistoricCreador(
            @ApiParam(value = "Nombre de pàgina", example = "1") @RequestParam(defaultValue = "1", value = "page", required = false) Integer page,
            @ApiParam(value = "Valors per pàgina", example = "10") @RequestParam(defaultValue = "10", value = "rpp", required = false) Integer rpp,
            @ApiParam(value = "Id usuari creador", example = "0") @RequestParam(defaultValue = "0", value = "idUsuariCreador", required = true) Long idUsuariCreador) {

        Page<ContracteDTO> filtrePage = contracteService.findPaginatedHistoricCreador(rpp, page, idUsuariCreador);
        return ResponseEntity.ok(filtrePage);
    }

    @Override
    @PostMapping(value = "/byCreador")
    public ResponseEntity<Page<ContracteDTO>> findPaginatedCreador(
            @ApiParam(value = "Nombre de pàgina", example = "1") @RequestParam(defaultValue = "1", value = "page", required = false) Integer page,
            @ApiParam(value = "Valors per pàgina", example = "10") @RequestParam(defaultValue = "10", value = "rpp", required = false) Integer rpp,
            @ApiParam(value = "Id usuari creador", example = "0") @RequestParam(defaultValue = "0", value = "idUsuariCreador", required = true) Long idUsuariCreador) {

        Page<ContracteDTO> filtrePage = contracteService.findPaginatedCreador(rpp, page, idUsuariCreador);
        return ResponseEntity.ok(filtrePage);
    }


}
