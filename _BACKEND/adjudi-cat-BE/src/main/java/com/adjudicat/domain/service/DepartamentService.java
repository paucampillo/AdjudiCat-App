package com.adjudicat.domain.service;

import com.adjudicat.controller.dto.DepartamentDTO;
import com.adjudicat.exception.AdjudicatBaseException;

import java.util.List;
import java.util.Set;

public interface DepartamentService {
    public void saveDepartaments(List<DepartamentDTO> DepartamentDTOs) throws AdjudicatBaseException;
    public Set<Long> getAllCodiDepartamentEns();

    DepartamentDTO getDepartament(Long codi);
}
