package com.adjudicat.controller.impl;

import com.adjudicat.controller.api.DepartamentAPI;
import com.adjudicat.domain.service.DepartamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/adj/departament")
public class DepartamentController extends BaseController implements DepartamentAPI {

    private final DepartamentService departamentService;
}
