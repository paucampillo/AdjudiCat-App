package com.adjudicat.controller.impl;

import com.adjudicat.controller.api.IdiomesAPI;
import com.adjudicat.controller.dto.IdiomesDTO;
import com.adjudicat.domain.service.IdiomesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/adj/idiomes")
public class IdiomesController extends BaseController implements IdiomesAPI {

    private final IdiomesService idiomesService;

    @Override
    @GetMapping("/listar")
    public ResponseEntity<List<IdiomesDTO>> listar() {
        return new ResponseEntity<>(idiomesService.listar(), HttpStatus.OK);
    }
}
