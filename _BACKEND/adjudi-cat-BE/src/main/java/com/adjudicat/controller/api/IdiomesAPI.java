package com.adjudicat.controller.api;


import com.adjudicat.controller.dto.IdiomesDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = {"Idiomes API"})
public interface IdiomesAPI {

    @ApiOperation(value = "Listar idiomas")
    ResponseEntity<List<IdiomesDTO>> listar();

}
