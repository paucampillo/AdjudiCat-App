package com.adjudicat.domain.impl;

import com.adjudicat.controller.dto.DepartamentDTO;
import com.adjudicat.domain.model.mapper.DepartamentMapper;
import com.adjudicat.domain.service.DepartamentService;
import com.adjudicat.exception.AdjudicatBaseException;
import com.adjudicat.exception.RepositoryException;
import com.adjudicat.exception.constants.RepositoryConstants;
import com.adjudicat.repository.repository.DepartamentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DepartamentServiceImpl implements DepartamentService {

    private final DepartamentRepository departamentRepository;
    private final DepartamentMapper departamentMapper;

    @Override
    @Transactional
    public void saveDepartaments(List<DepartamentDTO> DepartamentDTOs) throws AdjudicatBaseException {
        for (DepartamentDTO dto : DepartamentDTOs) {
            try {
                departamentRepository.save(departamentMapper.toEntity(dto));
            } catch (DataAccessException e) {
                throw new RepositoryException(e, RepositoryConstants.ERROR_BBDD);
            }
        }
    }


    @Override
    public Set<Long> getAllCodiDepartamentEns() {
        List<Long> codiDepartamentEns = departamentRepository.findAllCodiDepartamentEns();
        return new HashSet<>(codiDepartamentEns);
    }

    @Override
    public DepartamentDTO getDepartament(Long codi) {
        return departamentMapper.toDTO(departamentRepository.findByCodi(codi));
    }

}
