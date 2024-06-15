package com.adjudicat.domain.impl;

import com.adjudicat.controller.dto.LotDTO;
import com.adjudicat.domain.model.mapper.LotMapper;
import com.adjudicat.domain.service.LotService;
import com.adjudicat.exception.AdjudicatBaseException;
import com.adjudicat.exception.RepositoryException;
import com.adjudicat.exception.constants.RepositoryConstants;
import com.adjudicat.repository.entity.LotEntity;
import com.adjudicat.repository.repository.LotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LotServiceImpl implements LotService {

    private final LotRepository lotRepository;
    private final LotMapper lotMapper;

    @Override
    @Transactional
    public void saveLots(List<LotDTO> LotDTOs) throws AdjudicatBaseException {
        for (LotDTO dto : LotDTOs) {
            try {
                lotRepository.save(lotMapper.toEntity(dto));
            } catch (DataAccessException e) {
                throw new RepositoryException(e, RepositoryConstants.ERROR_BBDD);
            }
        }
    }
    @Override
    public Set<String> getAllNumeroLot() {
        List<String> numeroLots = lotRepository.findAllNumeroLots();
        return new HashSet<>(numeroLots);
    }

    @Override
    public LotDTO getLot(String numero) {
        return lotMapper.toDTO(lotRepository.findByNumero(numero));
    }

    public List<LotDTO> buscar(String nombre){
        List<LotEntity> list = lotRepository.findAllByDescripcioContainingIgnoreCaseOrderByDescripcio(nombre).orElse(null);
        if (list != null && !list.isEmpty()) {
            return list.stream()
                    .map(lotMapper::toDTO)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

}
