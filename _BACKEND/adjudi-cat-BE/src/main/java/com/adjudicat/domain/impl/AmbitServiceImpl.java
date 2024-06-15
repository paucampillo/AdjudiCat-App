package com.adjudicat.domain.impl;

import com.adjudicat.controller.dto.AmbitDTO;
import com.adjudicat.domain.model.mapper.AmbitMapper;
import com.adjudicat.domain.service.AmbitService;
import com.adjudicat.exception.AdjudicatBaseException;
import com.adjudicat.exception.RepositoryException;
import com.adjudicat.exception.constants.RepositoryConstants;
import com.adjudicat.repository.repository.AmbitRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AmbitServiceImpl implements AmbitService {

    private final AmbitRepository ambitRepository;
    private final AmbitMapper ambitMapper;

    @Override
    @Transactional
    public void saveAmbits(List<AmbitDTO> AmbitDTOs) throws AdjudicatBaseException {
        for (AmbitDTO dto : AmbitDTOs) {
            try {
                ambitRepository.save(ambitMapper.toEntity(dto));
            } catch (DataAccessException e) {
                throw new RepositoryException(e, RepositoryConstants.ERROR_BBDD);
            }
        }
    }

    @Override
    public Set<String> getAllCodiAmbit() {
        List<String> codiAmbits = ambitRepository.findAllCodiAmbits();
        return new HashSet<>(codiAmbits);
    }

    @Override
    public AmbitDTO getAmbit(Long codi) {
        return ambitMapper.toDTO(ambitRepository.findByCodi(codi));
    }

    @Override
    public List<AmbitDTO> listar() {
        return ambitRepository.findAll().stream()
                .map(ambitMapper::toDTO)
                .collect(Collectors.toList());
    }

}
