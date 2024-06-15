package com.adjudicat.domain.impl;

import com.adjudicat.controller.dto.RolsDTO;
import com.adjudicat.domain.model.mapper.RolsMapper;
import com.adjudicat.domain.service.RolsService;
import com.adjudicat.enums.RolsEnum;
import com.adjudicat.repository.repository.RolsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RolsServiceImpl implements RolsService {

    private final RolsRepository rolsRepository;
    private final RolsMapper rolsMapper;

    @Override
    public List<RolsDTO> listar() {
        return rolsRepository.findAllByVisibleIsTrue().stream()
                .map(rolsMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RolsDTO getRol(RolsEnum code) {
        return rolsMapper.toDTO(rolsRepository.findByCodi(code.toString()));
    }
}
