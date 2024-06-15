package com.adjudicat.controller.api;

import com.adjudicat.controller.dto.OrganDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = {"Organ API"})
public interface OrganAPI {

    @ApiOperation(value = "Buscador de organ por filtro nombre")
    ResponseEntity<List<OrganDTO>> buscar(String nombre);
}
