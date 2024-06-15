package com.adjudicat.controller.impl;

import com.adjudicat.controller.api.AmbitAPI;
import com.adjudicat.controller.api.LotAPI;
import com.adjudicat.controller.dto.AmbitDTO;
import com.adjudicat.controller.dto.LotDTO;
import com.adjudicat.domain.service.AmbitService;
import com.adjudicat.domain.service.LotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/adj/ambit")
public class AmbitController extends BaseController implements AmbitAPI {


    private final AmbitService ambitService;

    @Override
    @GetMapping("/listar")
    public ResponseEntity<List<AmbitDTO>> listar() {
        return ResponseEntity.ok(ambitService.listar());
    }
}
