package com.adjudicat.domain.impl;

import com.adjudicat.controller.dto.ValoracioCustomDTO;
import com.adjudicat.controller.dto.ValoracioDTO;
import com.adjudicat.domain.model.mapper.UsuariMapper;
import com.adjudicat.domain.model.mapper.ValoracioMapper;
import com.adjudicat.domain.service.UsuariService;
import com.adjudicat.domain.service.ValoracioService;
import com.adjudicat.repository.entity.UsuariEntity;
import com.adjudicat.repository.entity.ValoracioEntity;
import com.adjudicat.repository.repository.ValoracioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ValoracioServiceImpl implements ValoracioService {

    private final ValoracioRepository valoracioRepository;
    private final ValoracioMapper valoracioMapper;
    private final UsuariMapper usuariMapper;
    private final UsuariService usuariService;

    @Override
    @Transactional
    public void saveValoracio(ValoracioCustomDTO valoracioDTO) {
        ValoracioEntity valoracioEntity = valoracioMapper.toEntityCustom(valoracioDTO);
        valoracioEntity.setReceptor(usuariMapper.toEntity(usuariService.get(valoracioDTO.getIdReceptor())));
        valoracioEntity.setAutor(usuariMapper.toEntity(usuariService.get(valoracioDTO.getIdAutor())));
        valoracioEntity.setDataHoraValoracio(LocalDateTime.now());
        valoracioRepository.save(valoracioEntity);
    }

    @Override
    @Transactional
    public void deleteValoracio(Long idValoracio) {
        valoracioRepository.deleteById(idValoracio);
    }

    @Override
    public Page<ValoracioDTO> findPaginatedReceptor(Integer rpp, Integer page, Long idUsuari) {
        Pageable pageable = PageRequest.of(page-1, rpp);
        Page<ValoracioEntity> valoracioEntities = valoracioRepository.findByIdUsuari(idUsuari, pageable);
        return valoracioEntities.map(valoracioMapper::toDTO);
    }

    @Override
    public Page<ValoracioDTO> findPaginatedEmisor(Integer rpp, Integer page, Long idUsuari) {
        Pageable pageable = PageRequest.of(page-1, rpp);
        Page<ValoracioEntity> valoracioEntities = valoracioRepository.findByIdUsuariEmisor(idUsuari, pageable);
        return valoracioEntities.map(valoracioMapper::toDTO);
    }
}
