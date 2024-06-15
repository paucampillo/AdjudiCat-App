package com.adjudicat.domain.service;

import com.adjudicat.controller.dto.IdiomesDTO;
import com.adjudicat.enums.IdiomaEnum;
import com.adjudicat.repository.entity.IdiomesEntity;

import java.util.List;

public interface IdiomesService {

    List<IdiomesDTO> listar();
    IdiomesDTO getIdioma(IdiomaEnum code);
}
