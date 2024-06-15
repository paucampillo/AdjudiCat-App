package com.adjudicat.domain.service;

import com.adjudicat.controller.dto.OrganDTO;
import com.adjudicat.exception.AdjudicatBaseException;

import java.util.List;
import java.util.Set;

public interface OrganService {
    public void saveOrgans(List<OrganDTO> OrganDTOs) throws AdjudicatBaseException;
    public Set<Long> getAllCodiOrgan();

    OrganDTO getOrgan(Long codi);

    List<OrganDTO> buscar(String nombre);

}
