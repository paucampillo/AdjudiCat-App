package com.adjudicat.controller.api;

import com.adjudicat.controller.dto.LotDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = {"Lot API"})
public interface LotAPI {

    @ApiOperation(value = "Buscador de lote por filtro nombre")
    ResponseEntity<List<LotDTO>> buscar(String nombre);
}
