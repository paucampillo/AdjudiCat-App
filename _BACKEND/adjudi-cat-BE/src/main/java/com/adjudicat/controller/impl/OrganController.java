package com.adjudicat.controller.impl;

import com.adjudicat.controller.api.OrganAPI;
import com.adjudicat.controller.dto.OrganDTO;
import com.adjudicat.domain.service.OrganService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/adj/organ")
public class OrganController extends BaseController implements OrganAPI {

    private final OrganService organService;

    @Override
    @GetMapping("/buscar")
    public ResponseEntity<List<OrganDTO>> buscar(@RequestParam(value = "nom") final String nombre) {
        return ResponseEntity.ok(organService.buscar(nombre));
    }
}
