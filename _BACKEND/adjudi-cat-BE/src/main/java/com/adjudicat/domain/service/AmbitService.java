package com.adjudicat.domain.service;

import com.adjudicat.controller.dto.AmbitDTO;
import com.adjudicat.exception.AdjudicatBaseException;

import java.util.List;
import java.util.Set;

public interface AmbitService {

    void saveAmbits(List<AmbitDTO> AmbitDTOs) throws AdjudicatBaseException;

    Set<String> getAllCodiAmbit();

    AmbitDTO getAmbit(Long codi);

    List<AmbitDTO> listar();
}