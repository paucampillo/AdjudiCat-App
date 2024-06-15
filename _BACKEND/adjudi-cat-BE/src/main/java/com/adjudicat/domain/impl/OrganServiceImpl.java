package com.adjudicat.domain.impl;

import com.adjudicat.controller.dto.OrganDTO;
import com.adjudicat.domain.model.mapper.OrganMapper;
import com.adjudicat.domain.service.DepartamentService;
import com.adjudicat.domain.service.OrganService;
import com.adjudicat.exception.AdjudicatBaseException;
import com.adjudicat.exception.RepositoryException;
import com.adjudicat.exception.constants.RepositoryConstants;
import com.adjudicat.repository.entity.OrganEntity;
import com.adjudicat.repository.repository.OrganRepository;
import jakarta.transaction.Transactional;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganServiceImpl implements OrganService {

    private final OrganRepository organRepository;
    private final OrganMapper organMapper;
    private final DepartamentService departamentService;

    @Override
    @Transactional
    public void saveOrgans(List<OrganDTO> OrganDTOs) throws AdjudicatBaseException {
        for (OrganDTO dto : OrganDTOs) {
            try {
                if (dto.getDepartament() != null) dto.setDepartament(departamentService.getDepartament(dto.getDepartament().getCodi()));
                organRepository.save(organMapper.toEntity(dto));
            } catch (DataAccessException e) {
                throw new RepositoryException(e, RepositoryConstants.ERROR_BBDD);
            }
        }
    }


    @Override
    public Set<Long> getAllCodiOrgan() {
        List<Long> codiOrgans = organRepository.findAllCodiOrgan();
        return new HashSet<>(codiOrgans);
    }

    @Override
    public OrganDTO getOrgan(Long codi) {
        return organMapper.toDTO(organRepository.findByCodi(codi));
    }

    @Override
    public List<OrganDTO> buscar(String nombre) {
        List<OrganEntity> list = organRepository.findAllByNomContainingIgnoreCaseOrderByNom(nombre).orElse(null);
        if (list != null && !list.isEmpty()) {
            return list.stream()
                    .map(organMapper::toDTO)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

}
