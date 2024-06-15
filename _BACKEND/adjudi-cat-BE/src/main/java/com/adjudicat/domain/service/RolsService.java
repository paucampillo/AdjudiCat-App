package com.adjudicat.domain.service;

import com.adjudicat.controller.dto.RolsDTO;
import com.adjudicat.enums.IdiomaEnum;
import com.adjudicat.enums.RolsEnum;

import java.util.List;

public interface RolsService {

    List<RolsDTO> listar();
    RolsDTO getRol(RolsEnum code);
}
