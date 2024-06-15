package com.adjudicat.controller.impl;

import com.adjudicat.controller.api.MissatgeAPI;
import com.adjudicat.controller.dto.ConversacionDTO;
import com.adjudicat.controller.dto.MissatgeCustomDTO;
import com.adjudicat.domain.model.mapper.MissatgeMapper;
import com.adjudicat.domain.service.MissatgeService;
import com.adjudicat.exception.RepositoryException;
import io.swagger.annotations.ApiParam;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/adj/missatge")
public class MissatgeController extends BaseController implements MissatgeAPI {

    private final MissatgeService missatgeService;
    private final MissatgeMapper missatgeMapper;

    @Override
    @PostMapping(value = "/enviar")
    public ResponseEntity<Object> enviarMissatge(
            @ApiParam(value = "ID del emisor", example = "1") @RequestParam(value = "idEmisor", required = true) Long idEmisor,
            @ApiParam(value = "ID del receptor", example = "2") @RequestParam(value = "idReceptor", required = true) Long idReceptor,
            @ApiParam(value = "Mensaje", example = "Hola") @RequestParam(value = "mensaje", required = true) String mensaje
    ) {
        missatgeService.enviarMissatge(idEmisor, idReceptor, mensaje);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @DeleteMapping(value = "/{idMissatge}")
    public ResponseEntity<Object> deleteMissatge(@PathVariable("idMissatge") final Long idMissatge) {
        missatgeService.deleteMissatge(idMissatge);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @PostMapping(value = "/historic")
    public ResponseEntity<Page<MissatgeCustomDTO>> findPaginated(
            @ApiParam(value = "Num pagina", example = "1") @RequestParam(defaultValue = "1", value = "page", required = false) Integer page,
            @ApiParam(value = "Items per page", example = "30") @RequestParam(defaultValue = "20", value = "rpp", required = false) Integer rpp,
            @ApiParam(value = "ID del emisor", example = "1") @RequestParam(value = "idEmisor", required = true) Long idEmisor,
            @ApiParam(value = "ID del receptor", example = "2") @RequestParam(value = "idReceptor", required = true) Long idReceptor
    ) {
        Page<MissatgeCustomDTO> filtrePage = missatgeMapper.toPageCustomDTO(missatgeService.findPaginatedHistoric(rpp, page, idEmisor, idReceptor));
        return ResponseEntity.ok(filtrePage);
    }

    @Override
    @GetMapping(value = "/conversacions")
    public ResponseEntity<List<ConversacionDTO>> findConverses(@RequestParam(value = "idUsuari") final Long idUsuari) {
        return ResponseEntity.ok(missatgeService.findConverses(idUsuari));
    }

    @Override
    @DeleteMapping(value = "/deleteConverse")
    public ResponseEntity<Object> deleteConverse(@RequestParam(value = "idEmissor") final Long idEmissor, @RequestParam(value = "idReceptor") final Long idReceptor) {
        missatgeService.deleteConverse(idEmissor, idReceptor);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
