package com.adjudicat.controller.impl;

import com.adjudicat.controller.api.RolsAPI;
import com.adjudicat.controller.dto.RolsDTO;
import com.adjudicat.domain.service.RolsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/adj/rols")
public class RolsController extends BaseController implements RolsAPI {

    private final RolsService rolsService;

    @Override
    @GetMapping("/listar")
    public ResponseEntity<List<RolsDTO>> listar() {
        return new ResponseEntity<>(rolsService.listar(), HttpStatus.OK);
    }
}
