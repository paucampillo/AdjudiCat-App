package com.adjudicat.controller.api;


import com.adjudicat.controller.dto.RolsDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = {"Usuari API"})
public interface RolsAPI {

    @ApiOperation(value = "Listar roles visibles")
    ResponseEntity<List<RolsDTO>>listar();

}
