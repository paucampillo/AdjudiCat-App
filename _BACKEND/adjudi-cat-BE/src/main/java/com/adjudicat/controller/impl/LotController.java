package com.adjudicat.controller.impl;

import com.adjudicat.controller.api.LotAPI;
import com.adjudicat.controller.dto.LotDTO;
import com.adjudicat.controller.dto.OrganDTO;
import com.adjudicat.domain.service.LotService;
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
@RequestMapping("api/v1/adj/lot")
public class LotController extends BaseController implements LotAPI {


    private final LotService lotService;

    @Override
    @GetMapping("/buscar")
    public ResponseEntity<List<LotDTO>> buscar(@RequestParam(value = "nom") final String nombre) {
        return ResponseEntity.ok(lotService.buscar(nombre));
    }
}
