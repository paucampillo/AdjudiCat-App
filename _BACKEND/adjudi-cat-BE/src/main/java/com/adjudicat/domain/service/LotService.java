package com.adjudicat.domain.service;

import com.adjudicat.controller.dto.LotDTO;
import com.adjudicat.exception.AdjudicatBaseException;
import com.adjudicat.repository.entity.LotEntity;

import java.util.List;
import java.util.Set;

public interface LotService {
    public void saveLots(List<LotDTO> LotDTOs) throws AdjudicatBaseException;
    public Set<String> getAllNumeroLot();

    LotDTO getLot(String numero);

    List<LotDTO> buscar(String nombre);
}
