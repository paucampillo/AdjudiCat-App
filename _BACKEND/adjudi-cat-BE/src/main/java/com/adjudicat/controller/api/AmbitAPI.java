package com.adjudicat.controller.api;

import com.adjudicat.controller.dto.AmbitDTO;
import com.adjudicat.controller.dto.LotDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = {"Ambit API"})
public interface AmbitAPI {

    @ApiOperation(value = "Listar")
    ResponseEntity<List<AmbitDTO>> listar();
}
